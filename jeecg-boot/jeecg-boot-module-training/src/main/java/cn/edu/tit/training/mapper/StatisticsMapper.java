package cn.edu.tit.training.mapper;

import cn.edu.tit.training.entity.TraRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsMapper extends BaseMapper<TraRecord> {

/*    @Select ("select d.depart_name name,count(*) total from sys_depart d,tra_record t ,sys_user u where  t.user_id = u.username and  u.depart_ids = d.id GROUP BY u.depart_ids ORDER BY total asc")*/
    @Select("select depart_name departname,count(*) total from  sys_depart d LEFT JOIN sys_user u on d.id = u.depart_ids LEFT JOIN tra_record r on u.username = r.user_id  GROUP BY depart_ids ORDER BY total")
    public List<Map<Object,Object>> getStatisticsByDep();

    @Select("select r.user_id,count(*) total from tra_record r GROUP BY r.user_id ORDER BY  total desc limit 0,#{pageSize}")
    public List<Map<Object,Object>> getStatisticsByPer(@Param("pageSize") Integer pageSize);

    @Select("select count(*) from tra_record")
    public Long findTotalDecCount();

    @Select("select count(1) from tra_record where  create_time >= #{dayStart} and create_time < #{dayEnd}")
    public Long todayDecCount(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);

    @Select("select count(distinct(user_id)) from tra_record  where create_time >= #{dayStart} and create_time < #{dayEnd}")
    public Long findTodayUser(@Param("dayStart")Date dayStart, @Param("dayEnd")Date dayEnd);

    /**
     * 首页：根据时间统计访问数量/ip数量
     *
     * @param dayStart
     * @param dayEnd
     * @return
     */
    @Select("select count(*) as visit ,count(distinct(user_id)) as user_id ,DATE_FORMAT(create_time, '%Y-%m-%d') as tian ,DATE_FORMAT(create_time, '%m-%d') as type from tra_record where  create_time >= #{dayStart} and create_time < #{dayEnd} group by tian,type order by tian asc")
    public  List<Map<String, Object>> findDecCount(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);

}
