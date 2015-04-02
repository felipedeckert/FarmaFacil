package com.example.farmafacil_v1_3_2;

import com.example.farmafacil_v1_3_2.R;
import com.example.farmafacil_v1_3_2.ServiceHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.EditText;
/*
import com.example.farmafacil_v1_3_2.GetContacts;
import com.example.farmafacil_v1_3_2.HomeActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
*/

public class BuscaFragment extends Fragment{

	private View viewBuscaFragment;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		viewBuscaFragment = inflater.inflate(R.layout.fragment_busca, null);

		initializeUI();

		return viewBuscaFragment;
	}
	String remedio;
	private static String url = "http://farmafacil.leorocha.com/remedios/preco?nome=";//"http://www.mocky.io/v2/53c1bccb1e325cc31772f335";//R$10.00
	private void initializeUI() {
		Button buttonSearch = (Button) viewBuscaFragment.findViewById(R.id.bt_buscar);
		final EditText nomeRemedio = (EditText) viewBuscaFragment.findViewById(R.id.txt_busca);
		
		buttonSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					remedio  = nomeRemedio.getText().toString();
					url += URLEncoder.encode(remedio, "UTF-8");
					nomeRemedio.setText("");
				} catch (UnsupportedEncodingException e){ 
					//e.printStackTrace();
				}
				buscaMedicamento(v);
			}
		});
	}

	public void buscaMedicamento(View view){
		
		new GetContacts().execute();
	}
		
	private ProgressDialog pDialog;
	 
    // URL de onde vem o JSON
    
 
    // JSON Node names
    private static final String TAG_FARMACIAS = "farmacias";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "nome";
    private static final String TAG_PRICE = "preco";
 
    // farmacias JSONArray
    JSONArray farmacias = null;
    
    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
     
        // HASH MAP PARA VER O PREÇO MAIS BAIXO ENCONTRADO
        ArrayList<HashMap<String, String>> farmaciaList = new ArrayList<HashMap<String, String>>();
    	//public String [] f = new String[2];
        @Override
        
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Buscando medicamento...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
 
            // FAZ UMA REQUISIÇÃO E PEGA A RESPOSTA
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            
            /*- 
             * 
             * PRINTA NO LOG CAT CADA LINHA DO JSON 
             * 
             * -*/
            Log.d("Response: ", "> " + jsonStr);
            url = "http://farmafacil.leorocha.com/remedios/preco?nome=";
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    farmacias = jsonObj.getJSONArray(TAG_FARMACIAS);
                    
                    // looping through All Contacts
                    for (int i = 0; i < farmacias.length(); i++) {
                        JSONObject c = farmacias.getJSONObject(i);
                        
                        String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        //f[i] = name;
                        String price = c.getString(TAG_PRICE);
                        
                        /*- 
                         * 
                         * PRINTA NO LOG CAT O QUE ACABOU DE SER PEGO DO OBJETO JSON 
                         * 
                         * -*/
                        //Log.d("ID", id);
                        //Log.d("NAME", name);
                        //Log.d("PRICE", price);
                        //Log.d("FARMACIAS", String.valueOf(farmacias.length()));
                        
                        // hashmap temporário pra receber uma farmácia
                        HashMap<String, String> farmacia = new HashMap<String, String>();
                        
                        // Colocando os atributos da farmacia no hashmap temp (CHILD)
                     
                        farmacia.put(TAG_ID, id);
                        farmacia.put(TAG_NAME, name);
                        farmacia.put(TAG_PRICE, price);
                        
                        farmaciaList.add(farmacia);
                        
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
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            
            AlertDialog dialog;
    		//following code will be in your activity.java file 
    		//final String[] items = f;//{getString(R.string.drogasil), getString(R.string.droga_raia), getString(R.string.drogaria_onofre), getString(R.string.drogaria_sao_paulo)};
    		// arraylist to keep the selected items
    		//final ArrayList selectedItems = new ArrayList();

    		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    		//Se o preço encontrado for maior que zero, exiba-o e mostre opção de mostrar no mapa
    		if(farmaciaList.get(0).get(TAG_ID) != "null" && Double.parseDouble(farmaciaList.get(0).get(TAG_PRICE)) > 0){
    			builder.setTitle(R.string.escolha_as_farmacias);
    			builder.setMessage(farmaciaList.get(0).get(TAG_NAME)+"\n"+"Melhor preço encontrado: R$"+farmaciaList.get(0).get(TAG_PRICE))
    			// Set the action buttons
    			.setPositiveButton("Mostrar", new DialogInterface.OnClickListener() {
    			@Override
    				public void onClick(DialogInterface dialog, int id) {
    					//  Your code when user clicked on Mostrar
    				
    					Intent intent = new Intent(getActivity(), MapActivity.class);
	    				//intent.putExtra("selectedItems", selectedItems);
	    				startActivity(intent);
	    				//finish();    		                    
	    			}
	    		})
	    		.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	    			@Override
	    			public void onClick(DialogInterface dialog, int id) {
	    				//  Your code when user clicked on Cancelar
	    			}
	    		});
    		//se preço <= zero, então remédio não encontrado, mostrar aviso.
    		}else{
    		
    			builder.setTitle("Medicamento não encontrado");
    			builder.setMessage("Não foi encontrado nenhum medicamento com esse nome.")
    			// Set the action buttons
	    		.setNegativeButton("OK", new DialogInterface.OnClickListener() {
	    			@Override
	    			public void onClick(DialogInterface dialog, int id) {
	    				//  Your code when user clicked on OK
	    			}
	    		});
    		}	
    		dialog = builder.create();
    		dialog.show();
        }
 
    }
 
}
