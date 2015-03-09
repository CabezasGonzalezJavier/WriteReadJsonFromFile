package com.thedeveloperworldisyours.writereadjsonfromfile.requesttask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.thedeveloperworldisyours.writereadjsonfromfile.MainActivity;
import com.thedeveloperworldisyours.writereadjsonfromfile.utils.Utils;

public class RequestTask extends AsyncTask<String, String, String>{
	

	private ProgressDialog mDialog;
	private Activity mActivity;
	public RequestTask(MainActivity activity) {
        mDialog = new ProgressDialog(activity);
        mActivity = activity;
    }
	
    @Override
	protected void onPreExecute() {
    	mDialog.show();
		super.onPreExecute();
	}

	@Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(uri[0]));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mDialog.dismiss();
        Log.d("RequestTask", result.toString());
        Utils.writeToFile(result, mActivity);
    }
}