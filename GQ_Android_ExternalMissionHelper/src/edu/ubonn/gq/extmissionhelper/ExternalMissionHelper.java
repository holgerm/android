package edu.ubonn.gq.extmissionhelper;

import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ExternalMissionHelper {

	public static final String RES_PREFIX = "res:";
	public static final String ARG_PREFIX = "arg:";

	private Activity activity;
	private boolean parametersInitialized = false;
	private String[] inputParameterNames;
	private String[] inputParameterValues;
	private String[] resultNames;
	private String[] resultValues;

	public ExternalMissionHelper(Activity externalAppActivity) {
		this.activity = externalAppActivity;
	}

	public int getNumberOfInputParameters() {
		if (!parametersInitialized)
			initializeParameters();
		return inputParameterNames.length;
	}

	public String[] getInputParameterNames() {
		if (!parametersInitialized)
			initializeParameters();
		return inputParameterNames;
	}

	public String getInputParameterName(int i) {
		return inputParameterNames[i];
	}

	public String getInputParameterValue(int i) {
		return inputParameterValues[i];
	}

	/**
	 * @param name
	 * @return the current value of the input parameter or <code>null</code> if
	 *         no such parameter exists.
	 */
	public String getInputParameterValue(String name) {
		int index = getIndex(inputParameterNames, name);
		if (index >= 0)
			return inputParameterValues[index];
		else
			return null;
	}

	public String[] getInputParameterValues() {
		if (!parametersInitialized)
			initializeParameters();
		return inputParameterValues;
	}

	public int getNumberOfOutputParameters() {
		if (!parametersInitialized)
			initializeParameters();
		return resultNames.length;
	}

	public String getOutputParameterName(int i) {
		return resultNames[i];
	}

	public String[] getOutputParameterNames() {
		if (!parametersInitialized)
			initializeParameters();
		return resultNames;
	}

	public String getOutputParameterValue(int i) {
		return resultValues[i];
	}

	public String[] getOutputParameterValues() {
		if (!parametersInitialized)
			initializeParameters();
		return resultValues;
	}

	/**
	 * @param name
	 * @return the current value of the output parameter or <code>null</code> if
	 *         no such parameter exists.
	 */
	public String getOutputParameterValue(String name) {
		int index = getIndex(resultNames, name);
		if (index >= 0)
			return resultValues[index];
		else
			return null;
	}

	public void setOutputParameterValue(int i, String value) {
		resultValues[i] = value;
	}

	public boolean setOutputParameterValue(String name, String value) {
		int index = getIndex(resultNames, name);
		if (index == -1)
			return false;
		else {
			resultValues[index] = value;
			return true;
		}
	}

	private int getIndex(String[] array, String name) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(name)) {
				return i;
			}
		}
		return -1;
	}

	private void initializeParameters() {
		Bundle paramsBundle = activity.getIntent().getExtras();
		// count arguments and results and create storage arrays:
		int args = 0, results = 0;
		if (paramsBundle != null) {
			for (Iterator<String> iterator = paramsBundle.keySet().iterator(); iterator
					.hasNext();) {
				String currentParam = iterator.next();
				if (currentParam.startsWith(ARG_PREFIX))
					args++;
				if (currentParam.startsWith(RES_PREFIX))
					results++;
			}
		}
		inputParameterNames = new String[args];
		inputParameterValues = new String[args];
		resultNames = new String[results];
		resultValues = new String[results];

		// collect and store arguments and results:
		int args_i = 0, results_i = 0;
		if (paramsBundle != null) {
			for (Iterator<String> iterator = paramsBundle.keySet().iterator(); iterator
					.hasNext();) {
				String currentParam = iterator.next();
				if (currentParam.startsWith(ARG_PREFIX)) {
					inputParameterNames[args_i] = currentParam.substring(4);
					inputParameterValues[args_i++] = paramsBundle
							.getString(currentParam);
				}
				if (currentParam.startsWith(RES_PREFIX)) {
					resultNames[results_i] = currentParam.substring(4);
					resultValues[results_i++] = paramsBundle
							.getString(currentParam);
				}
			}
		}
		parametersInitialized = true;
	}

	/**
	 * Prepares the resulting intent of the according activity which is given
	 * back to the calling GeoQuest activity. You must call this method before
	 * calling finish() or within your own implementation of finish() in your
	 * external activity in order to dispatch return values back to GeoQuest.
	 */
	public void finish() {
		Intent resultIntent = new Intent();
		for (int i = 0; i < resultNames.length; i++) {
			resultIntent.putExtra(RES_PREFIX + resultNames[i], resultValues[i]);
		}
		activity.setResult(Activity.RESULT_OK, resultIntent);
	}

	/**
	 * @param currentKey
	 *            the map or bundle key which refers to a result parameter.
	 * @return the pure name of the result parameter or <code>null</code> if the
	 *         give key was not a valid result parameter key.
	 */
	public static String extractResultName(String currentKey) {
		if (currentKey.startsWith(RES_PREFIX))
			return currentKey.substring(RES_PREFIX.length());
		return null;
	}
}
