//package com.example.movierating.db.mappers;
//
//import com.example.movierating.db.po.Collection;
//
//public interface CollectionMapper {
//    int deleteByPrimaryKey(Integer collectionId);
//
//    int insert(Collection record);
//
//    int insertSelective(Collection record);
//
//    Collection selectByPrimaryKey(Integer collectionId);
//
//    int updateByPrimaryKeySelective(Collection record);
//
//    int updateByPrimaryKey(Collection record);
//}
package com.example.movierating.db.mappers;

import com.example.movierating.db.po.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectionMapper {
    int insertSelective(Collection record);

    int updateByPrimaryKeySelective(Collection record);

    Collection selectByPrimaryKey(Integer collectionId);

    List<Collection> selectByUserId(Integer userId);

    Collection selectByUserIdAndTrailId(@Param("userId") Integer userId, @Param("trailId") Integer trailId);

    int deleteByUserIdAndTrailId(@Param("userId") Integer userId, @Param("trailId") Integer trailId);
    
    List<Collection> selectByUserIdAndType(@Param("userId") Integer userId, @Param("collectionType") String collectionType);
}