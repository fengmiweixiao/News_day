package com.example.dskbase;



import java.util.List;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;


public class LiteDb {
	private  DataBase newInstance;

	public LiteDb(Context context) {
		newInstance = LiteOrm.newInstance(context, "letedb.db");
	}
	//添加
	public <T> void Add(T t) {
		newInstance.save(t);
	}
	//查找所有
	public <T> List<T> select(Class<T> t) {
		return newInstance.query(t);
	}
	//根据条件查找
	public <T> List<T> getQueryByWhere(Class<T> cla, String field,
			String[] value) {
		return newInstance.query(new QueryBuilder(cla).where(field,
				value));
	}
	//根据条件删除
	public <T> void deleteWhere(Class<T> cla, String field, String[] value) {
		newInstance.delete(cla, WhereBuilder.create()
				.where(field , value));
	}

	
	// 仅在以存在时修改
	 
	public <T> void update(T t) {
		newInstance.update(t, ConflictAlgorithm.Replace);
	}
}
