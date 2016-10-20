package com.example.adapter;





import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CommonAdapter<T> extends BaseAdapter  {
	Context mContext; 
    public List<T> mList;
    int resource=-1; 
    LayoutInflater mLayoutInflater;
    /**
     * 
     * @param mContext 
     * @param mList
     * @param resource ��Ӧ��layout��xml����R.id.XXXX
     */
    public CommonAdapter(Context mContext, List<T> mList, int resource) {
        super();
        this.mContext = mContext;
       
        this.mList = mList;
        this.resource = resource;
        mLayoutInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public T getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	 /**
     * getView�м���View���������õĴ���������������ṩһ�����󷽷����Զ���ʵ�֡�
     * @param commonViewHolder ��ǰ��ViewHolder
     * @param currentView    ��ǰ��View
     * @param item    ��ӦView������
     */
    public void  setViewData(View currentView,T item) {
    	
    }

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		  // TODO Auto-generated method stub
        if(view==null){
            view=mLayoutInflater.inflate(resource, null);
        }
        setViewData(view,getItem(position));
        ((ViewGroup)view).setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        return view;

	}
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
