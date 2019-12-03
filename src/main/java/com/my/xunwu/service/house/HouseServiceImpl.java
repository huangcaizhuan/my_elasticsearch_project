package com.my.xunwu.service.house;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.my.xunwu.base.HouseStatus;
import com.my.xunwu.base.LoginUserUtil;
import com.my.xunwu.entity.House;
import com.my.xunwu.entity.HouseDetail;
import com.my.xunwu.entity.HousePicture;
import com.my.xunwu.entity.HouseTag;
import com.my.xunwu.entity.Subway;
import com.my.xunwu.entity.SubwayStation;
import com.my.xunwu.repository.HouseDetailRepository;
import com.my.xunwu.repository.HousePictureRepository;
import com.my.xunwu.repository.HouseRepository;
import com.my.xunwu.repository.HouseTagRepository;
import com.my.xunwu.repository.SubwayRepository;
import com.my.xunwu.repository.SubwayStationRepository;
import com.my.xunwu.service.ServiceMultiResult;
import com.my.xunwu.service.ServiceResult;
import com.my.xunwu.web.dto.HouseDTO;
import com.my.xunwu.web.dto.HouseDetailDTO;
import com.my.xunwu.web.dto.HousePictureDTO;
import com.my.xunwu.web.form.DataTableSearch;
import com.my.xunwu.web.form.HouseForm;
import com.my.xunwu.web.form.PhotoForm;

/**
 * 房屋接口实现类
 * @author hcz
 * @date 2019.10.29
 */
@Service
public class HouseServiceImpl implements IHouseService{

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private HouseRepository houseRepository;
	@Autowired
	private SubwayRepository subwayRepository;
	@Autowired
	private SubwayStationRepository subwayStationRepository;
	@Autowired
	private HouseDetailRepository houseDetailRepository;
	@Autowired
	private HousePictureRepository housePictureRepository;
	@Autowired
	private HouseTagRepository houseTagRepository;
	
	@Value("${qiniu.cdn.prefix}")
    private String cdnPrefix;
	
	@Override
	public ServiceResult<HouseDTO> save(HouseForm houseForm) {
		HouseDetail detail =new HouseDetail();
		ServiceResult<HouseDTO> subwayValidtionResult = wrapperDetailInfo(detail,houseForm);
		if(subwayValidtionResult != null ) {
			return subwayValidtionResult;
		}
		 
		House house = new House();
		modelMapper.getConfiguration().setFullTypeMatchingRequired(true);//规定必须字段名完全匹配
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);//将匹配策略定义为严格
		modelMapper.map(houseForm, house);
		
		Date now = new Date();
		house.setCreateTime(now);
		house.setLastUpdateTime(now);
		house.setAdminId(LoginUserUtil.getLonginUserId());
		houseRepository.save(house);
		
		detail.setHouseId(house.getId());
		houseDetailRepository.save(detail);
		
		List<HousePicture> pictures = generatePictures(houseForm, house.getId());
        Iterable<HousePicture> housePictures = housePictureRepository.save(pictures);
        
        HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
        HouseDetailDTO houseDetailDTO = modelMapper.map(detail, HouseDetailDTO.class);

        houseDTO.setHouseDetail(houseDetailDTO);
        
        List<HousePictureDTO> pictureDTOS = new ArrayList<>();
        housePictures.forEach(housePicture -> pictureDTOS.add(modelMapper.map(housePicture, HousePictureDTO.class)));
        houseDTO.setPictures(pictureDTOS);
        houseDTO.setCover(this.cdnPrefix + houseDTO.getCover());

        List<String> tags = houseForm.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<HouseTag> houseTags = new ArrayList<>();
            for (String tag : tags) {
                houseTags.add(new HouseTag(house.getId(), tag));
            }
            houseTagRepository.save(houseTags);
            houseDTO.setTags(tags);
        }

        return new ServiceResult<HouseDTO>(true, null, houseDTO);
	}
	 /**
     * 图片对象列表信息填充
     * @param form
     * @param houseId
     * @return
     */
    private List<HousePicture> generatePictures(HouseForm form, Long houseId) {
    	List<HousePicture> pictures = new ArrayList<>();
    	if(form.getPhotos() == null || form.getPhotos().isEmpty()) {
    		return pictures;
    	}
    	
    	for(PhotoForm photoForm:form.getPhotos()) {
    		HousePicture picture = new HousePicture();
    		picture.setHouseId(houseId);
            picture.setCdnPrefix(cdnPrefix);
            picture.setPath(photoForm.getPath());
            picture.setWidth(photoForm.getWidth());
            picture.setHeight(photoForm.getHeight());
            pictures.add(picture);
    	}
    	return pictures;
    }
	/**
     * 房源详细信息对象填充
     * @param houseDetail
     * @param houseForm
     * @return
     */
	private ServiceResult<HouseDTO> wrapperDetailInfo(HouseDetail detail,HouseForm houseForm) {
		Subway subway =subwayRepository.findOne(houseForm.getSubwayLineId());
		if(subway == null) {
			return new ServiceResult<>(false, "Not valid subway line!");
		}
		
		SubwayStation subwayStation = subwayStationRepository.findOne(houseForm.getSubwayStationId());
		if(subwayStation == null || subway.getId() != subwayStation.getSubwayId()) {
			return new ServiceResult<>(false, "Not valid subway station!");
		}
		
		detail.setSubwayLineId(subway.getId());
		detail.setSubwayLineName(subway.getName());
		detail.setSubwayStationId(subwayStation.getId());
		detail.setSubwayStationName(subwayStation.getName());
		
		detail.setDescription(houseForm.getDescription());
		detail.setDetailAddress(houseForm.getDetailAddress());
		detail.setLayoutDesc(houseForm.getLayoutDesc());
		detail.setRentWay(houseForm.getRentWay());
		detail.setRoundService(houseForm.getRoundService());
		detail.setTraffic(houseForm.getTraffic());
		return null;
	}
	
	@Override
	public ServiceMultiResult<HouseDTO> adminQuery(DataTableSearch searchBody) {
		List<HouseDTO> houseDTOs = new ArrayList<>();
		//分页
		Sort sort = new Sort(Sort.Direction.fromString(searchBody.getDirection()),searchBody.getOrderBy());
		int page = searchBody.getStart()/searchBody.getLength();
		Pageable pageable = new PageRequest(page, searchBody.getLength(),sort);
		
		//条件查询
		Specification<House> specification = (root,query,cb)->{
			Predicate predicate = cb.equal(root.get("adminId"), LoginUserUtil.getLonginUserId());
			predicate = cb.and(predicate,cb.notEqual(root.get("status"), HouseStatus.DELETED.getValue()));
			
			if(searchBody.getCity() != null) {
				predicate = cb.and(predicate,cb.equal(root.get("cityEnName"), searchBody.getCity()));
			}
			if(searchBody.getStatus() != null) {
				predicate = cb.and(predicate,cb.equal(root.get("status"), searchBody.getStatus()));
			}
			if(searchBody.getCreateTimeMin() != null) {
				predicate = cb.and(predicate,cb.greaterThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMin()));
			}
			if(searchBody.getCreateTimeMax() != null) {
				predicate = cb.and(predicate,cb.lessThanOrEqualTo(root.get("createTime"), searchBody.getCreateTimeMax()));
			}
			if(searchBody.getTitle() != null) {
				predicate = cb.and(predicate,cb.like(root.get("title"), "%"+searchBody.getTitle()+"%"));
			}
			return predicate;
		};
		Page<House> houses = houseRepository.findAll(specification,pageable);//分页+条件查询
		
		//Page<House> houses = houseRepository.findAll(pageable);//分页没条件查询
		//Iterable<House> houses = houseRepository.findAll();//查询所有
		houses.forEach(house->{
			HouseDTO houseDTO = modelMapper.map(house, HouseDTO.class);
			houseDTO.setCover(this.cdnPrefix+house.getCover());
			houseDTOs.add(houseDTO);
		});
		//return new ServiceMultiResult<>(houseDTOs.size(), houseDTOs);//不分页
		return new ServiceMultiResult<>(houses.getTotalElements(), houseDTOs);//分页
	}
}
