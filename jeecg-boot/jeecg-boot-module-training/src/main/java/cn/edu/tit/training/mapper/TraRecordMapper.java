package cn.edu.tit.training.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.edu.tit.training.entity.TraRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @Description: 培训记录
 * @Author: jeecg-boot
 * @Date: 2020-04-12
 * @Version: V1.0
 */


public interface TraRecordMapper extends BaseMapper<TraRecord> {

    @Select("select ID_ from act_ru_execution where BUSINESS_KEY_ =#{businessKey}")
    public String getInstanceIDByBusinessKey(@Param("businessKey") String businessKey);

    @Select("select * from tra_record where check_flag =#{traRecord.checkFlag} limit #{start},#{end}")
    public List<TraRecord> getCheckedTra(@Param("start") Integer start, @Param("end")Integer end, @Param("traRecord")TraRecord traRecord);

}
