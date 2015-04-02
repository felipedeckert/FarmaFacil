package com.example.farmafacil_v1_3_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import android.widget.ExpandableListView;
import android.widget.TextView;

public class ListReceitasActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receitas_list_activity);
		
		initializeUI();
	}
	List<String> listDataHeader = new ArrayList<String>();
    HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
	
	private void initializeUI() {
		// call object that creates Expandable ListView
		new GetContacts().execute();
		
	}


	//-----------------------------------------------------------------------------------
	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context context;
		private List<String> listDataHeader; // header titles
	    // child data in format of header title, child title
	    private HashMap<String, List<String>> listDataChild;
		
		public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listDataChild) {
			this.context = context;
			this.listDataHeader = listDataHeader;
			this.listDataChild = listDataChild;
		}
		
		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
	            boolean isLastChild, View convertView, ViewGroup parent) {
			
			final String childText = (String) getChild(groupPosition, childPosition);
			 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.receita_list_child, null);
	        }
	 
	        TextView labelListChild = (TextView) convertView.findViewById(R.id.label_remedio);
	 
	        labelListChild.setText(childText);
	        return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return listDataHeader.get(groupPosition); 
		}

		@Override
		public int getGroupCount() {
			return listDataHeader.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			String headerTitle = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.receita_list_group, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView.findViewById(R.id.label_name_receita);
	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	 
	        return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
		
	}
	private ProgressDialog pDialog;
	 
    // URL to get contacts JSON
    private static String url = "http://farmafacil.leorocha.com/receitas/listar";
 
    // JSON Node names
    private static final String TAG_RECEITAS = "receitas";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "nome";//nome do medicamento
    private static final String TAG_FREQUENCIA = "frequencia";
    private static final String TAG_DOSAGEM = "dosagem";
    private static final String TAG_MEDICO = "medico";
    private static final String TAG_DATA = "data";
    private static final String TAG_PRAZO = "prazo";
    private static final String TAG_CONTINUO = "uso_continuo";
    // drugstores JSONArray
    JSONArray receitas = null;
 
    
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
    	// Hashmap for ListView
        ArrayList<HashMap<String, String>> receitaList = new ArrayList<HashMap<String, String>>();
    	//public String [] f = new String[2];
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Criar um ProgressDialog
            pDialog = new ProgressDialog(ListReceitasActivity.this);
            pDialog.setMessage("Buscando as receitas...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    receitas = jsonObj.getJSONArray(TAG_RECEITAS);
                    
                    // For every medicine foun in the JSON file
                    for (int i = 0; i < receitas.length(); i++) {
                    	// Get a medicine object
                        JSONObject c = receitas.getJSONObject(i);
                        // Get the components of every medicine 
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String dosagem = c.getString(TAG_DOSAGEM);
                        String prazo = c.getString(TAG_PRAZO);
                        String medico = c.getString(TAG_MEDICO);
                        String data = c.getString(TAG_DATA);
                        String continuo = c.getString(TAG_CONTINUO);
                        String frequencia = c.getString(TAG_FREQUENCIA);
                       
                        // temp hashmap for a medicine
                        HashMap<String, String> receita = new HashMap<String, String>();
                        
                        
                        // add the components to the hashmap
                        receita.put(TAG_ID, id);
                        receita.put(TAG_NAME, name);
                        receita.put(TAG_DOSAGEM, dosagem);
                        receita.put(TAG_PRAZO, prazo);
                        receita.put(TAG_MEDICO, medico);
                        receita.put(TAG_DATA, data);
                        receita.put(TAG_CONTINUO, continuo);
                        receita.put(TAG_FREQUENCIA, frequencia);
                        
                        // add medicine to the list of medicines
                        receitaList.add(receita);
                        
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss ProgrssDialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            for (int i = 0; i < receitaList.size(); i++) {
            	// creates the header of medicine with its name
            	listDataHeader.add(receitaList.get(i).get(TAG_NAME));
            	
            	// temp arrayList with the info that will be shown about the medicine (CHILD)
            	List<String> remedio = new ArrayList<String>();
            	// add the desire properties 
                remedio.add("Dosagem: "+receitaList.get(i).get(TAG_DOSAGEM));
                remedio.add("Frequência: "+receitaList.get(i).get(TAG_FREQUENCIA));
                remedio.add("Prazo: "+receitaList.get(i).get(TAG_PRAZO)+" dias");
                remedio.add("Médico: "+receitaList.get(i).get(TAG_MEDICO));
                remedio.add("Data: "+receitaList.get(i).get(TAG_DATA));
            	
                // link child CHILD to HEADER
                listDataChild.put(listDataHeader.get(i), remedio); 
            }
            // creates expandable listView
            ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
            // call adapter that shows list above
            expandableListView.setAdapter(new ExpandableListAdapter(ListReceitasActivity.this, listDataHeader, listDataChild));
        }
 
    }
}
