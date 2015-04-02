package com.example.farmafacil_v1_3_2;
/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.farmafacil_v1_3_2.ListReceitasActivity.ExpandableListAdapter;

import android.content.Context;

import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

 
public class ReceitasFragment extends Fragment {
 
	private View viewReceitasFragment;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        viewReceitasFragment = inflater.inflate(R.layout.fragment_receitas, container, false);
        
        initializeUI();
        
        return viewReceitasFragment;
    }

	private void initializeUI() {
		Button buttonSearch = (Button) viewReceitasFragment.findViewById(R.id.bt_buscar_receitas);
		
		buttonSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				buscaReceitas(v);
			}
		});
	}
	public void buscaReceitas(View v){
		new BuscaReceita().execute();
	}
	private ProgressDialog pDialog;
	 
    // URL to get contacts JSON
    private static String url = "http://farmafacil.leorocha.com/receitas/listar";
 
    // JSON Node names
    private static final String TAG_RECEITAS = "receitas";
    /*
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_DOSAGEM = "dosagem";
    private static final String TAG_USO = "uso";
    */ 
    // farmacias JSONArray
    JSONArray receitas = null;
 
    
    /**
     * Async task class to get json by making HTTP call
     * */
    private class BuscaReceita extends AsyncTask<Void, Void, Void> {
    	//List<String> listDataHeader = new ArrayList<String>();
        //HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
    	// Hashmap for ListView
        //ArrayList<HashMap<String, String>> receitaList = new ArrayList<HashMap<String, String>>();
    	//public String [] f = new String[2];
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Criar um ProgressDialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Buscando receitas...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            //Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    receitas = jsonObj.getJSONArray(TAG_RECEITAS);
                    
                    
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
            // Remove o ProgrssDialog
            if (pDialog.isShowing())
                pDialog.dismiss();
          //Se tiver receitas, chama a activity da expandable list
            if(receitas != null && receitas.length() > 0){
            	Intent intent = new Intent(getActivity(), ListReceitasActivity.class);
				startActivity(intent);
            }
            //Exibe mensagem "N„o h· receitas"
            else{
            	AlertDialog dialog;
        		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        		builder.setTitle("N„o h· receitas");
    			builder.setMessage("N„o foi encontrada nenhuma receita associada a esse usu·rio.")
    			// Set the action buttons
	    		.setNegativeButton("OK", new DialogInterface.OnClickListener() {
	    			@Override
	    			public void onClick(DialogInterface dialog, int id) {
	    				//  Your code when user clicked on OK
	    			}
	    		});
    			dialog = builder.create();
        		dialog.show();
            }
            
        }
 
    }
	/*/-------------------------------------------------------------------
	public class ReceitasListAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		
		public ReceitasListAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			return 6;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.receitas_list_item, null);
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					//chama a Activity que tem a lista de receitas e remédios
					Intent intent = new Intent(getActivity(), ListReceitasActivity.class);
					startActivity(intent);
				}
			});
			
			return convertView;
		}
		
	}*/
}