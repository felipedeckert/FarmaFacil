package com.example.farmafacil_v1_3_2;
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
 
    // farmacias JSONArray
    JSONArray receitas = null;
 
    
    /**
     * Async task class to get json by making HTTP call
     * */
    private class BuscaReceita extends AsyncTask<Void, Void, Void> {
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
            // Remove ProgrssDialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            // If there is any prescriptoin, calls activity to show expandable list
            if(receitas != null && receitas.length() > 0){
            	Intent intent = new Intent(getActivity(), ListReceitasActivity.class);
				startActivity(intent);
            }
            // Show "no prescription" message
            else{
            	AlertDialog dialog;
        		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        		builder.setTitle("Não há receitas");
    			builder.setMessage("Não foi encontrada nenhuma receita associada a esse usuário.")
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
}