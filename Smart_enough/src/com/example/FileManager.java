package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class FileManager {
	private AssetManager assetManager;
	private Context context;
	private ArrayList<String> wholeFileList = new ArrayList<String>();
	
	public FileManager(Context activityContext) {
		context = activityContext;
	}
	
	public String[] readQuestionFile() {
		String line;
		try {
			assetManager = context.getAssets();
			InputStream inputStream = assetManager.open(context.getString(R.string.filemanager_QuestionFile));
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while ((line = bufferedReader.readLine()) != null){
				wholeFileList.add(line);
			}
			bufferedReader.close();
		} catch (Exception e) {
			Log.e("login activity", e.toString());
		}
		return wholeFileList.toArray(new String[wholeFileList.size()]);
	}
	
	private ArrayList<String> readScoreFile() {
		ArrayList<String> arrayListScores = new ArrayList<String>();
		String output;
		try {
			InputStream inputStream = context.openFileInput(context.getString(R.string.filemanager_ScoresFile));
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			while ((output = bufferedReader.readLine()) != null){
				arrayListScores.add(output);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
		}
		return arrayListScores;
	}
	
	private void writeScoreFile(String data) {
	    try {
	    	FileOutputStream fileOutputStream = context.openFileOutput(
	    			context.getString(R.string.filemanager_ScoresFile),
	    			Context.MODE_APPEND
	    	);
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
	        outputStreamWriter.write(data);
	        outputStreamWriter.append("\n");
	        outputStreamWriter.close();
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	    } 
	}
}
