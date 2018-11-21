package com.zombieinawaterbottle.footballpredictor;


import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment.SavedState;
import android.app.FragmentManager.BackStackEntry;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Groups extends Fragment implements OnClickListener {
	Button addGroup;
	ArrayList<Friends> friends;
	ArrayList<String> friend_name;
	String matchday;
	String user_id;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.groups, container, false);
		addGroup=(Button) v.findViewById(R.id.createNewGroup);
		addGroup.setOnClickListener(this);
        getActivity().getActionBar().setTitle("GROUPS");
        final ArrayList<Group> group_arrayList=(ArrayList<Group>) getArguments().getSerializable("group_details");
        final HashMap<String, String> user=(HashMap<String, String>) getArguments().getSerializable("user_details");
        matchday=user.get("latest_matchday");
    	user_id=user.get("user_id");
        friends=(ArrayList<Friends>) getArguments().getSerializable("friends_list");
    	friend_name=new ArrayList<String>();
    	for (int i=0;i<friends.size();i++){
    		friend_name.add(friends.get(i).username);
    	}
		Group_ListView adapter = new Group_ListView(getActivity(), group_arrayList);
		ListView list = (ListView) v.findViewById(R.id.myGroups);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            	Bundle bundle=getArguments();
            	Fragment members= new GroupMembersView(group_arrayList.get(position).group_id);
            	FragmentManager frm=getFragmentManager();
        		members.setArguments(bundle);

            	frm.beginTransaction().replace(R.id.container, members).addToBackStack(null).commit();
            }
            
		});
            
        return v;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v==addGroup){
			
		if (friend_name.size()==0){
			Toast.makeText(getActivity(), "You need to have atleast one friend to create a group!", Toast.LENGTH_SHORT).show();
			return;
		}
		Dialog dialog=new Dialog(getActivity());
		dialog.setTitle("Enter Group Name and Members");
		dialog.setContentView(R.layout.newgroup_form);
		EditText name= (EditText) dialog.findViewById(R.id.enter_group_name);
		ListView members= (ListView) dialog.findViewById(R.id.participants);
		TextView matchday=(TextView) dialog.findViewById(R.id.matchday);
		matchday.setText("MATCHDAY : "+this.matchday);
		Button create=(Button) dialog.findViewById(R.id.createGroup);
		final Friend_List adp=new Friend_List(getActivity(), friend_name);
		members.setAdapter(adp);
		create.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String members=user_id;
				for (int i=0;i<friends.size();i++){
					if (adp.isMember.get(i)){
						members=members+"/"+friends.get(i).id;
					}
				}
				Toast.makeText(getActivity(), members, Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
		}
		
	}
	public class Friend_List extends BaseAdapter{
		Context context;
		LayoutInflater inflater;
		ArrayList<String> friends_name;
		ArrayList<Boolean> isMember;
		public Friend_List(Context context, ArrayList<String> friends_name) {
			// TODO Auto-generated constructor stub
			this.context=context;
			inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.friends_name=friends_name;
			isMember=new ArrayList<Boolean>();
			for (int i=0;i<friends_name.size();i++){
				isMember.add(false);
			}
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return friends_name.size();
		}

		@Override
		public String getItem(int pos) {
			// TODO Auto-generated method stub
			return friends_name.get(pos);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int pos, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View v=inflater.inflate(R.layout.add_member_row, null);
			CheckBox check=(CheckBox) v.findViewById(R.id.isMember);
			TextView name=(TextView) v.findViewById(R.id.selectFriend);
			name.setText(friends_name.get(pos));
			check.setChecked(isMember.get(pos));
			check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton v, boolean state) {
					// TODO Auto-generated method stub
					isMember.set(pos, state);
				}
			});
			return v;
		}
		
	}
}
