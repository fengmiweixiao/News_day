package com.example.dskbase;

import java.util.List;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import android.content.Context;

public class UserLiteDb {
	private DataBase newInstance;

	public UserLiteDb(Context context){
		newInstance = LiteOrm.newInstance(context, "userlitedb.db");
	}
	//���
		public <T> void Add(T t) {
			newInstance.save(t);
		}
		//��������
		public <T> List<T> select(Class<T> t) {
			return newInstance.query(t);
		}
		//������������
		public <T> List<T> getQueryByWhere(Class<T> cla, String field,
				String[] value) {
			return newInstance.query(new QueryBuilder(cla).where(field,
					value));
		}
		//��������ɾ��
		public <T> void deleteWhere(Class<T> cla, String field, String[] value) {
			newInstance.delete(cla, WhereBuilder.create()
					.where(field , value));
		}

		
		// �����Դ���ʱ�޸�
		 
		public <T> void update(T t) {
			newInstance.update(t, ConflictAlgorithm.Replace);
		}
	}


