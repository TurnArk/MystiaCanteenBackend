package com.javaweb.mystiacanteen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.javaweb.mystiacanteen.entity.AllSelection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AllSelectionMapper extends BaseMapper<AllSelection> {
    @Select("(\n" +
            "  SELECT *" +
            "  FROM dish\n" +
            "  ORDER BY buy DESC\n" +
            ")\n" +
            "UNION ALL\n" +
            "(\n" +
            "  SELECT *" +
            "  FROM drink\n" +
            "  ORDER BY buy DESC\n" +
            ")\n" +
            "UNION ALL\n" +
            "(\n" +
            "  SELECT *" +
            "  FROM food\n" +
            "  ORDER BY buy DESC\n" +
            ")\n" +
            "UNION ALL\n" +
            "(\n" +
            "  SELECT *" +
            "  FROM kitchenware\n" +
            "  ORDER BY buy DESC\n" +
            ")\n" +
            "ORDER BY buy DESC\n")
    List<AllSelection> selectAllSelectionHot();

    @Select("(\n" +
            "  SELECT *" +
            "  FROM dish\n" +
            "  ORDER BY click DESC\n" +
            ")\n" +
            "UNION ALL\n" +
            "(\n" +
            "  SELECT *" +
            "  FROM drink\n" +
            "  ORDER BY click DESC\n" +
            ")\n" +
            "UNION ALL\n" +
            "(\n" +
            "  SELECT *" +
            "  FROM food\n" +
            "  ORDER BY click DESC\n" +
            ")\n" +
            "UNION ALL\n" +
            "(\n" +
            "  SELECT *" +
            "  FROM kitchenware\n" +
            "  ORDER BY click DESC\n" +
            ")\n" +
            "ORDER BY click DESC\n")
    List<AllSelection> selectAllSelectionGood();

    @Select("SELECT * FROM dish UNION ALL\n"+
            "SELECT * FROM drink UNION ALL\n"+
            "SELECT * FROM food UNION ALL \n"+
            "SELECT * FROM kitchenware")
    List<AllSelection> selectionAll();
}
