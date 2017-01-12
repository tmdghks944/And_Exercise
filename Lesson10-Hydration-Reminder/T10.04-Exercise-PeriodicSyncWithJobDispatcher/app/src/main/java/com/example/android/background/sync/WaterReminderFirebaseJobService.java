/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.R;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;
public class WaterReminderFirebaseJobService extends JobService{
    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackgroundTask = new AsyncTask() {
             @Override
             protected Object doInBackground(Object[] params) {
                 Context context = WaterReminderFirebaseJobService.this;
                 ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                 return null;
             }

            @Override
            protected void onPostExecute(Object o) {
                    jobFinished(jobParameters, false);
            }
        };
        mBackgroundTask.execute();
        return true;
    }

    // TODO (11) Override onStopJob
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // TODO (12) If mBackgroundTask is valid, cancel it
        // TODO (13) Return true to signify the job should be retried
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
