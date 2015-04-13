package com.mythos.demo.example.mina;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mythos.demo.R;

/**
 * Description		: mina聊天界面的Adapter
 * 
 * 
 * <br><br>Time		: 2015-2-7  下午7:48:21
 * 
 * @version 1.0
 * 
 * @since 1.0
 * 
 * @author YLM
 */
public class MinaChatViewAdapter extends BaseAdapter {
	
    private static final String TAG = MinaChatViewAdapter.class.getSimpleName();

    private List<MinaChatMessage> cell;

    private Context ctx;
    
    private LayoutInflater mInflater;

    public MinaChatViewAdapter(Context context, List<MinaChatMessage> cell) {
        ctx = context;
        this.cell = cell;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return cell.size();
    }

    public Object getItem(int position) {
        return cell.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    


	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		MinaChatMessage message = cell.get(position);
	 	return message.getIsFromMe();
	}
	
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	MinaChatMessage message = cell.get(position);
    	int isFromMe = message.getIsFromMe();
    		
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	if (isFromMe == MinaChatMessage.OTHER) {
	    		convertView = mInflater.inflate(R.layout.item_chatting_msg_text_left, null);
	    	} else {
	    		convertView = mInflater.inflate(R.layout.item_chatting_msg_text_right, null);
	    	}

	    	viewHolder = new ViewHolder();
	    	viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
	    	viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
	    	viewHolder.isFromMe = isFromMe;
			  
	    	convertView.setTag(viewHolder);
	    }else{
	    	viewHolder = (ViewHolder) convertView.getTag();
	    }
	
	    
	    
	    viewHolder.tvSendTime.setText(message.getTime());
	    viewHolder.tvContent.setText(message.getContent());
	    
	    return convertView;
    }
    

    static class ViewHolder { 
        public TextView tvSendTime;
        public TextView tvContent;
        public int isFromMe = 0;
    }


}
