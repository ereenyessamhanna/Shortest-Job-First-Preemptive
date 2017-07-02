package com.egypt.ereeny_shortest_job_first_preemptive;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    Button nextBtn;
    Button submitBtn;
    LinearLayout nextLL;
    LinearLayout outerLL;
    ListView schedulingLV;
    EditText numberOfProcessesET;

    LinearLayout footer;
    TextView turnAroundAvgTV;
    TextView waitingAvgTV;
    TextView responseAvgTV;
    Button restartBtn;


    String processLinearLayout;
    String processName;
    String processArrivalTime;
    String processBurstTime;

    CustomAdapter customAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Shortest Job First");
        setContentView(R.layout.activity_main);

        numberOfProcessesET = (EditText) findViewById(R.id.numberOfProcessField);

        outerLL = (LinearLayout) findViewById(R.id.numberOfProcessLinearLayout);
        nextLL = (LinearLayout) findViewById(R.id.linearLayout);

        schedulingLV = (ListView) findViewById(R.id.listView);
        footer = (LinearLayout) getLayoutInflater().inflate(R.layout.footer_listview, null, false);
        schedulingLV.addFooterView(footer);

        turnAroundAvgTV = (TextView) findViewById(R.id.turnAroundAvg);
        waitingAvgTV = (TextView) findViewById(R.id.waitingTimeAvg);
        responseAvgTV = (TextView) findViewById(R.id.responseTimeAvg);
        restartBtn = (Button) findViewById(R.id.restartBtn);

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedulingLV.setVisibility(View.GONE);
                turnAroundAvgTV.setText(null);
                waitingAvgTV.setText(null);
                responseAvgTV.setText(null);
                nextBtn.setEnabled(true);
                numberOfProcessesET.setEnabled(true);
                nextLL.setVisibility(View.VISIBLE);
            }
        });


        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean error = false;
                // boolean arrivalZeroExist = false;
                // this means that user is at 2nd phase ( enter Name - AT - BT )
                // So Checking fields
                // if it's valid we create process objects

                int processSize = Integer.parseInt(numberOfProcessesET.getText().toString());

                for(int i=0; i<processSize; i++) {

                    processName = "processName" + (i+1);
                    EditText processNameTemp = (EditText) findViewById(getResources().getIdentifier(processName, "id", getPackageName()));
                    if(processNameTemp.getText().length() == 0){
                        processNameTemp.setError("Process name is required");
                        error = true;
                    }

                    processArrivalTime = "processArrivalTime" + (i+1);
                    EditText arrivalTimeTemp = (EditText) findViewById(getResources().getIdentifier(processArrivalTime, "id", getPackageName()));
                    if(arrivalTimeTemp.getText().length() == 0){
                        arrivalTimeTemp.setError("AT" + (i+1) + " is required");
                        error = true;
                    }

                     /*   if(arrivalTimeTemp.getText().toString().trim().equals("0")){
                            arrivalZeroExist = true;
                        }*/

                    processBurstTime = "processBurstTime" + (i+1);
                    EditText burstTimeTemp = (EditText) findViewById(getResources().getIdentifier(processBurstTime, "id", getPackageName()));
                    if(burstTimeTemp.getText().length() == 0){
                        burstTimeTemp.setError("BT" + (i+1) + " is required");
                        error = true;
                    }
                }

                // if arrival time inputs doesn't has 0 tell user that he must enter at least one process  at 0
                   /* if( arrivalZeroExist == false){
                        Toast.makeText(MainActivity.this, "At least one process must have 0 arrival time", Toast.LENGTH_SHORT).show();
                    }*/

                // Start Scheduling
                if( error == false ){

                    outerLL.setVisibility(View.GONE);
                    submitBtn.setEnabled(false);
                    submitBtn.setVisibility(View.GONE);

                    Scheduler scheduler = new Scheduler();
                    scheduler.init();


                    for(int i=0; i<processSize; i++) {

                        ProcessModel process[] = new ProcessModel[processSize];

                        processName = "processName" + (i+1);
                        EditText processNameTemp = (EditText) findViewById(getResources().getIdentifier(processName, "id", getPackageName()));
                        String processNameValue = processNameTemp.getText().toString().trim();

                        processArrivalTime = "processArrivalTime" + (i+1);
                        EditText arrivalTimeTemp = (EditText) findViewById(getResources().getIdentifier(processArrivalTime, "id", getPackageName()));
                        int arrivalTimeValue = Integer.parseInt(arrivalTimeTemp.getText().toString().trim());

                        processBurstTime = "processBurstTime" + (i+1);
                        EditText burstTimeTemp = (EditText) findViewById(getResources().getIdentifier(processBurstTime, "id", getPackageName()));
                        int burstTimeValue = Integer.parseInt(burstTimeTemp.getText().toString().trim());

                        process[i] = new ProcessModel(arrivalTimeValue, burstTimeValue, processNameValue);
                        Toast.makeText(MainActivity.this, process[i].getName() + "   " + process[i].getArrivalTime() + "   " + process[i].getBurstTime(), Toast.LENGTH_SHORT).show();

                        scheduler.addProcess_Prim(process[i]);
                        scheduler.addProcess(process[i]);


                    }

                    MyApplication.initProcessAdapterModelArrayList();
                    scheduler.startScheduling();
                    schedulingLV.setVisibility(View.VISIBLE);

                    waitingAvgTV.setText("Waiting Avg: " + Float.toString(MyApplication.getWaitingAvg()));
                    waitingAvgTV.setTextColor(Color.YELLOW);

                    turnAroundAvgTV.setText("Turnaround Avg: " + Float.toString(MyApplication.getTurnAroundAvg()));
                    turnAroundAvgTV.setTextColor(Color.YELLOW);

                    responseAvgTV.setText("Response Avg: " + Float.toString(MyApplication.getResponseAvg()));
                    responseAvgTV.setTextColor(Color.YELLOW);


                    customAdapter = new CustomAdapter(getApplicationContext(), MyApplication.processAdapterModelArrayList);
                    schedulingLV.setAdapter(customAdapter);






                }

            }

        });

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberOfProcessesET.length() == 0 ){
                    numberOfProcessesET.setError("Number of processes is required");}
                else if ( Integer.parseInt(numberOfProcessesET.getText().toString()) <= 1){
                    numberOfProcessesET.setError("Minimum number of processes is 2");}
                else if ( Integer.parseInt(numberOfProcessesET.getText().toString()) >= 10){
                    numberOfProcessesET.setError("Maximum number of processes is 9");}
                else {
                    nextBtn.setEnabled(false);
                    numberOfProcessesET.setEnabled(false);
                    nextLL.setVisibility(View.GONE);
                    outerLL.setVisibility(View.VISIBLE);
                    outerLL.removeAllViewsInLayout();
                    submitBtn.setEnabled(true);
                    submitBtn.setVisibility(View.VISIBLE);

                    // Title Inner LinearLayout
                    LinearLayout titleLayout = new LinearLayout(MainActivity.this);
                    titleLayout.setOrientation(LinearLayout.HORIZONTAL);
                    titleLayout.setWeightSum(3f);

                    LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    titleParams.setMargins(20, 20, 20, 20);

                    titleLayout.setLayoutParams(titleParams);


                    // Title ( Process - BurstTime ) Layout
                    TextView processTV = new TextView(MainActivity.this);
                    processTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                    processTV.setText("Process Name");
                    processTV.setTextColor(Color.BLUE);

                    TextView burstTimeTV = new TextView(MainActivity.this);
                    burstTimeTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                    burstTimeTV.setText("Burst Time");
                    burstTimeTV.setTextColor(Color.RED);

                    LinearLayout.LayoutParams process_burst_params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    process_burst_params.setMargins(30, 0, 0, 0);
                    process_burst_params.weight = 1f;

                    processTV.setLayoutParams(process_burst_params);
                    burstTimeTV.setLayoutParams(process_burst_params);


                    // Title ( ArrivalTime ) Layout
                    TextView arrivalTimeTV = new TextView(MainActivity.this);
                    arrivalTimeTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                    arrivalTimeTV.setText("Arrival Time");
                    arrivalTimeTV.setTextColor(Color.GREEN);

                    LinearLayout.LayoutParams arrivalTimeParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    arrivalTimeParams.setMargins(60, 0, 0, 0);
                    arrivalTimeParams.weight = 1f;

                    arrivalTimeTV.setLayoutParams(arrivalTimeParams);


                    // Adding TextViews to Title LinearLayout
                    titleLayout.addView(processTV);
                    titleLayout.addView(arrivalTimeTV);
                    titleLayout.addView(burstTimeTV);

                    outerLL.addView(titleLayout);


                    // Creating each Process EditTexts Layout
                    int processSize = Integer.parseInt(numberOfProcessesET.getText().toString());

                    for (int i = 0 ; i < processSize; i++) {
                        // Strings for assign ids
                        processLinearLayout = "process" + (i+1) + "LL";
                        processName = "processName" + (i+1);
                        processArrivalTime = "processArrivalTime" + (i+1);
                        processBurstTime = "processBurstTime" + (i+1);

                        // Creating Process LinearLayout
                        LinearLayout processLL = new LinearLayout(MainActivity.this);
                        processLL.setOrientation(LinearLayout.HORIZONTAL);
                        processLL.setWeightSum(3f);
                        processLL.setLayoutParams(titleParams);
                        processLL.setId(getResources().getIdentifier(processLinearLayout, "id", getPackageName()));

                        // Process Name - BurstTime - ArrivalTime  Layout
                        EditText processNameET = new EditText(MainActivity.this);
                        processNameET.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                        processNameET.setId(getResources().getIdentifier(processName, "id", getPackageName()));
                        processNameET.setHint("P" + (i+1));


                        EditText burstTimeET = new EditText(MainActivity.this);
                        burstTimeET.setInputType(InputType.TYPE_CLASS_NUMBER);
                        burstTimeET.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                        burstTimeET.setId(getResources().getIdentifier(processBurstTime, "id", getPackageName()));
                        burstTimeET.setHint("BT" + (i+1));


                        EditText arrivalTimeET = new EditText(MainActivity.this);
                        arrivalTimeET.setInputType(InputType.TYPE_CLASS_NUMBER);
                        arrivalTimeET.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                        arrivalTimeET.setId(getResources().getIdentifier(processArrivalTime, "id", getPackageName()));
                        arrivalTimeET.setHint("AT" + (i+1));



                        processNameET.setLayoutParams(process_burst_params);
                        burstTimeET.setLayoutParams(process_burst_params);
                        arrivalTimeET.setLayoutParams(arrivalTimeParams);

                        //Adding EditTexts to Process LinearLayout
                        processLL.addView(processNameET);
                        processLL.addView(arrivalTimeET);
                        processLL.addView(burstTimeET);

                        //Adding process LinearLayout to the outer Activity LinearLayout
                        outerLL.addView(processLL);


                    }
                }

            }

        });

    }





}