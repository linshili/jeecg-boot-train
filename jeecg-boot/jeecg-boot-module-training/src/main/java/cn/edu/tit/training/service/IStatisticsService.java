package cn.edu.tit.training.service;

import cn.edu.tit.training.entity.TraRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IStatisticsService extends IService<TraRecord> {

    public List<Map<Object,Object>> getStatisticsByDep();
    public List<Map<Object,Object>> getStatisticsByPer(Integer pageSize);
    public Long findTotalDecCount();
    public Long todayDecCount(Date dayStart, Date dayEnd);
    public Long findTodayUser(Date dayStart, Date dayEnd);
    public  List<Map<String, Object>> findDecCount( Date dayStart,Date dayEnd);

}
