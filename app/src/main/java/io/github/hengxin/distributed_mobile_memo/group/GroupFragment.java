package io.github.hengxin.distributed_mobile_memo.group;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import io.github.hengxin.distributed_mobile_memo.R;
import io.github.hengxin.distributed_mobile_memo.group.member.SystemNode;

/**
 * A fragment representing a list of {@link SystemNode}s.
 * the data source is an ArrayList of {@link SystemNode}s maintained
 * by {@link GroupConfig}
 */
public class GroupFragment extends Fragment implements
        AbsListView.OnItemClickListener,
        JoinGroupDialog.IJoinGroupListener {
    /**
     * communication between this fragment and its popped dialog
     *
     * @see JoinGroupDialog
     * @see #addButtonListener(View)
     */
    protected static final int FRAGMENT_REQUEST_CODE = 0;

    private AbsListView server_replica_listview;

    // adapter used to populate the ListView/GridView with Views.
    private ArrayAdapter<SystemNode> server_replica_list_adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set adapter to show a list of {@link SystemNode}s
        this.server_replica_list_adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1,
                GroupConfig.INSTANCE.getGroupMembers());
    }

    /**
     * @inheritDoc
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        /**
         *  add and handle with button click listeners
         *  this consists of the main task of this MemoFragment;
         *  also of the main task of this app
         */
        this.addButtonListener(view);

        // Set the adapter
        this.server_replica_listview = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) this.server_replica_listview).setAdapter(this.server_replica_list_adapter);

        // Set OnItemClickListener so we can be notified on item clicks
        this.server_replica_listview.setOnItemClickListener(this);

        return view;
    }

    /**
     * add and handle with button listeners: adding/removing server replicas
     */
    private void addButtonListener(View view) {
        view.findViewById(R.id.imgbtn_add_group).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * show the "adding new replica dialog"
                 */
                JoinGroupDialog join_group_dialog = new JoinGroupDialog();
                join_group_dialog.setTargetFragment(GroupFragment.this, FRAGMENT_REQUEST_CODE);
                join_group_dialog.show(getFragmentManager(), getString(R.string.tag_join_group_dialog));
            }
        });

        /**
         *  // TODO: move to the onItemClick() method?
         */
        view.findViewById(R.id.imgbtn_delete_group).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * pre-install server replicas for test [BEGIN]
                 * @TODO: remove it
                 */

//				GroupConfig.INSTANCE.addReplica(new SystemNode("114.212.85.216"));	// tablet
//				GroupConfig.INSTANCE.addReplica(new SystemNode("114.212.84.134"));	// WHF phone

//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.110"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.111"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.112"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.113"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.114"));

//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.100"));
                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.101"));
                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.102"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.103"));
                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.104"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.105"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.106"));
                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.108"));
                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.109"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.128"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("192.168.1.128"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("114.212.87.48"));
//                GroupConfig.INSTANCE.addReplica(new SystemNode("114.212.86.14"));

                server_replica_list_adapter.notifyDataSetChanged();

                /**
                 * pre-install [END]
                 */
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    /**
     * @inheritDoc update the {@link ListView} of {@link SystemNode}s
     */
    @Override
    public void onJoinGroup(SystemNode replica) {
        GroupConfig.INSTANCE.addReplica(replica);
        this.server_replica_list_adapter.notifyDataSetChanged();
    }

}
