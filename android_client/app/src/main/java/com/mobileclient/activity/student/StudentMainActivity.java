 
package com.mobileclient.activity.student;
 

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.mobileclient.R;
import com.mobileclient.activity.MyTabActivity;
import com.mobileclient.activity.teacher.ClassInfoListActivity;
import com.mobileclient.activity.teacher.FirstClassListActivity;
import com.mobileclient.activity.teacher.MoreActivity;
import com.mobileclient.util.ActivityUtils;


public class StudentMainActivity extends MyTabActivity {

	private static final String FIRST_TAB = "first";
	private static final String SECOND_TAB = "second";
	private static final String THIRD_TAB = "third";

	private TabHost tabHost;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		tabHost = this.getTabHost();

		/*第一tab页 */
		TabSpec firstSpec = tabHost.newTabSpec(FIRST_TAB).setIndicator(FIRST_TAB)
				.setContent(new Intent(this, ClassInfoListActivity.class));
		Button firstBtn = (Button)findViewById(R.id.firstBtn);
		firstBtn.setText("选课&退课");
		/*第二tab页*/
		TabSpec secondSpec = tabHost.newTabSpec(SECOND_TAB).setIndicator(SECOND_TAB)
				.setContent(new Intent(this,ClassListActivity.class));
		Button secondBtn = (Button)findViewById(R.id.secondBtn);
		secondBtn.setText("签到");
		/*第三tab页*/
		TabSpec thirdSpec = tabHost.newTabSpec(THIRD_TAB).setIndicator(THIRD_TAB)
				.setContent(new Intent(this,MoreActivity.class));
		Button thirdBtn = (Button)findViewById(R.id.thirdBtn);
		thirdBtn.setText("更多");
		tabHost.addTab(firstSpec);
		tabHost.addTab(secondSpec);
		tabHost.addTab(thirdSpec);

		RadioGroup radioGroup = (RadioGroup) this
				.findViewById(R.id.rg_main_btns);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.firstBtn:
					tabHost.setCurrentTabByTag(FIRST_TAB);
					break;

				case R.id.secondBtn:
					tabHost.setCurrentTabByTag(SECOND_TAB);
					break;

				case R.id.thirdBtn:
					tabHost.setCurrentTabByTag(THIRD_TAB);
					break;

				default:
					break;
				}

			}
		});

	}



    public boolean dispatchKeyEvent(KeyEvent event) {
    	if (event.getAction() == KeyEvent.ACTION_DOWN
    			&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
    		ActivityUtils.getInstance().ConfirmExit(this);

    	}

    	return super.dispatchKeyEvent(event);
    };



}
