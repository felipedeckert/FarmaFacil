package com.example.farmafacil_v1_3_2;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.farmafacil_v1_3_2.adapter.TabsPagerAdapter;
import com.example.farmafacil_v1_3_2.helper.DatabaseHelper;

public class HomeActivity extends FragmentActivity implements ActionBar.TabListener{

	private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    
    //private Button btBuscar;
    //private EditText txtBusca;
    //private String nomeMedicamento;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        //depois extrair strings
        String[] tabs = {"Busca", "Receitas"};
        
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));}
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
         
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        
        /*
         * 
         * 
         *    TESTE DO BD
         * 
         * 
         * 
        db = new DatabaseHelper(getApplicationContext());
        
        // Creating tags
        Receita rec1 = new Receita("01/01/01", "Carlos", "123");
        Receita rec2 = new Receita("02/02/02", "Marlos", "1234");
        Receita rec3 = new Receita("03/03/03", "Varlos", "1235");
        //Tag tag4 = new Tag("Androidhive");
 
        // Inserting tags in db
        long rec1_id = db.createReceita(rec1);
        long rec2_id = db.createReceita(rec2);
        long rec3_id = db.createReceita(rec3);
        
        //long tag4_id = db.createTag(tag4);
 
        Log.d("Tag Count", "Tag Count: " + db.getTodasReceitas().size());
 
        // Creating ToDos
        Remedio todo1 = new Remedio("rem1", "1mg", "tome1");
        Remedio todo2 = new Remedio("rem2", "2mg", "tome2");
        Remedio todo3 = new Remedio("rem3", "3mg", "tome3");
 
        Remedio todo4 = new Remedio("rem4", "4mg", "tome4");
        Remedio todo5 = new Remedio("rem5", "5mg", "tome5");
        Remedio todo6 = new Remedio("rem6", "6mg", "tome6");
        Remedio todo7 = new Remedio("rem7", "7mg", "tome7");
 
        Remedio todo8 = new Remedio("rem8", "8mg", "tome8");
        Remedio todo9 = new Remedio("rem9", "9mg", "tome9");
 
        //Remedio todo10 = new Remedio("rem1", "1mg", "tome1");
        //Todo todo11 = new Todo("Take database backup", 0);
 
        // Inserting todos in db
        // Inserting todos under "Receita1" Tag
        long todo1_id = db.createRemedio(todo1, new long[] { rec1_id });
        long todo2_id = db.createRemedio(todo2, new long[] { rec1_id });
        long todo3_id = db.createRemedio(todo3, new long[] { rec1_id });
 
        // Inserting todos under "Receita2" Tag
        long todo4_id = db.createRemedio(todo4, new long[] { rec2_id });
        long todo5_id = db.createRemedio(todo5, new long[] { rec2_id });
        long todo6_id = db.createRemedio(todo6, new long[] { rec2_id });
        long todo7_id = db.createRemedio(todo7, new long[] { rec2_id });
 
        // Inserting todos under "Important" Tag
        long todo8_id = db.createRemedio(todo8, new long[] { rec3_id });
        long todo9_id = db.createRemedio(todo9, new long[] { rec3_id });
 
        // Inserting todos under "Androidhive" Tag
        //long todo10_id = db.createToDo(todo10, new long[] { tag4_id });
        //long todo11_id = db.createToDo(todo11, new long[] { tag4_id });
 
        Log.e("Todo Count", "Todo count: " + db.getRemedioCount());
 
        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        //db.createTodoTag(todo10_id, tag2_id);
 
        // Getting all tag names
        Log.d("Get Tags", "Getting All Tags");
 
        List<Receita> allTags = db.getTodasReceitas();
        for (Receita tag : allTags) {
            Log.d("Data Receita", tag.getData());
        }
 
        // Getting all Todos
        Log.d("Get Todos", "Getting All ToDos");
 
        List<Remedio> allToDos = db.getTodosRemedios();
        for (Remedio todo : allToDos) {
            Log.d("nome", todo.getNome());
        }
 
        /* Getting todos under "Watchlist" tag name
        Log.d("ToDo", "Get todos under single Tag name");
 
        List<Remedio> tagsWatchList = db.getAllToDosByTag(tag3.getTagName());
        for (Remedio todo : tagsWatchList) {
            Log.d("ToDo Watchlist", todo.getNote());
        }
 		*/
        
        /*
        // Deleting a ToDo
        Log.d("Deletar Remedio", "Deletando Remedio");
        Log.d("Tag Count", "Tag Count Before Deleting: " + db.getRemedioCount());
 
        db.deleteRemedio(todo8_id);
 
        Log.d("Tag Count", "Tag Count After Deleting: " + db.getRemedioCount());
 
        /* Deleting all Todos under "Shopping" tag
        Log.d("Tag Count",
                "Tag Count Before Deleting 'Shopping' Todos: "
                        + db.getToDoCount());
 
        db.deleteTag(tag1, true);
 
        Log.d("Tag Count",
                "Tag Count After Deleting 'Shopping' Todos: "
                        + db.getToDoCount());
 
        // Updating tag name
        tag3.setTagName("Movies to watch");
        db.updateTag(tag3);
 
        // Don't forget to close database connection
        db.closeDB();
        */
    	}
                   	
    	@Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
    		View focus = getCurrentFocus();
            if (focus != null) {
                hiddenKeyboard(focus);
            }
        }
     
        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // on tab selected
            // show respected fragment view
            viewPager.setCurrentItem(tab.getPosition());
        }
        //esconde teclado quando sai de uma tab
        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        	View focus = getCurrentFocus();
            if (focus != null) {
                hiddenKeyboard(focus);
            }
        }
        //Esxonde teclado
        private void hiddenKeyboard(View v) {
            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        
}
