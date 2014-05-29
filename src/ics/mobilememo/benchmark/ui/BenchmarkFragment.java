package ics.mobilememo.benchmark.ui;

import ics.mobilememo.R;
import ics.mobilememo.benchmark.executor.Executor;
import ics.mobilememo.benchmark.workload.PoissonWorkloadGenerator;
import ics.mobilememo.benchmark.workload.Request;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BenchmarkFragment extends Fragment
{
	private RadioGroup radio_grp_rw = null;
	private RadioButton radio_role = null;
	private EditText etxt_request_number = null;
	private EditText etxt_rate = null;
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public BenchmarkFragment()
	{
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_benchmark, container, false);

		this.radio_grp_rw = (RadioGroup) view.findViewById(R.id.radio_grp_rw);
		this.etxt_request_number = (EditText) view.findViewById(R.id.etxt_request_number);
		this.etxt_rate = (EditText) view.findViewById(R.id.etxt_rate);
		
		// handle with the click of the "Run the benchmark" button
		this.addButtonListener(view);
		
		return view;
	}
	
    /**
     * run the benchmark
     */
    private void addButtonListener(final View view)
    {
    	view.findViewById(R.id.btn_run).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				int role = BenchmarkFragment.this.getRoleChosen(v);
				int total_requests = Integer.parseInt(BenchmarkFragment.this.etxt_request_number.getText().toString());
				int rate = Integer.parseInt(BenchmarkFragment.this.etxt_rate.getText().toString());
				
				// establish the queue of requests between {@link PoissonWorkloadGenerator} and {@link Executor}
				BlockingQueue<Request> request_queue = new LinkedBlockingDeque<Request>();
				
				// start executor {@link Executor}
				(new Thread(new Executor(request_queue))).start();
				// block the {@link Executor} for a while
				try
				{
					Thread.sleep(5000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				// start workload {@link PoissonWorkload}
				(new Thread(new PoissonWorkloadGenerator(request_queue, role, total_requests, rate))).start();
			}
		});
    }
    
    /**
     * @param v view
     * @return the role (writer [0], reader [1]) chosen
     */
    private int getRoleChosen(View v)
    {
    	int roleId = this.radio_grp_rw.getCheckedRadioButtonId();
		this.radio_role = (RadioButton) v.findViewById(roleId);
		/**
		 * in the UI, 0 for writer; 1 for reader
		 * this is consistent with {@link Request#WRITE_TYPE} and {@link Request#READ_TYPE}
		 */
		return this.radio_grp_rw.indexOfChild(radio_role);
    }
}