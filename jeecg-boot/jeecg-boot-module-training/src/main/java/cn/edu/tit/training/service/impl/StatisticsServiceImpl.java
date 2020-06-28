package cn.edu.tit.training.service.impl;

import cn.edu.tit.training.entity.TraRecord;
import cn.edu.tit.training.mapper.StatisticsMapper;
import cn.edu.tit.training.service.IStatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper,TraRecord> implements IStatisticsService {
    @Autowired
    private StatisticsMapper staticticsMapper;

    public List<Map<Object,Object>> getStatisticsByDep(){
        return staticticsMapper.getStatisticsByDep();
    }

    public List<Map<Object,Object>> getStatisticsByPer(Integer pageSize){
        return staticticsMapper.getStatisticsByPer(pageSize);
    }

    @Override
    public Long findTotalDecCount() {
        return staticticsMapper.findTotalDecCount();
    }

    @Override
    public Long todayDecCount(Date dayStart, Date dayEnd) {
        return staticticsMapper.todayDecCount(dayStart,dayEnd);
    }

    @Override
    public Long findTodayUser(Date dayStart, Date dayEnd) {
        return staticticsMapper.findTodayUser(dayStart,dayEnd);
    }

    @Override
    public List<Map<String, Object>> findDecCount(Date dayStart, Date dayEnd) {
        return staticticsMapper.findDecCount(dayStart,dayEnd);
    }

}
