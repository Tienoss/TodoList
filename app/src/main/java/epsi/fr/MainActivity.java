package epsi.fr;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import epsi.fr.model.StorageHelper;
import epsi.fr.model.Todo;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
            SharedPreferences prefs = getSharedPreferences("epsi.prefs", 0);
            SharedPreferences.Editor ed = prefs.edit();
            ed.putInt("value", 42);
            ed.commit();
            Log.d("main", "value: " + prefs.getInt("value", 0));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        StorageHelper helper;
        ArrayList<String> dataList;
        List<Todo> todoList;
        TodoAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            helper = new StorageHelper(this.getActivity());

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            TextView txt = (TextView) rootView.findViewById(R.id.text);
            final EditText ed = (EditText) rootView.findViewById(R.id.editText);
            Button btn = (Button) rootView.findViewById(R.id.button);
            ListView lst = (ListView) rootView.findViewById(R.id.list);
            final Activity act = this.getActivity();


            todoList = helper.getAll();
            dataList = new ArrayList<String>();

            for (Todo t: todoList){
                dataList.add(t.title);
            }

            adapter = new TodoAdapter(this.getActivity(), todoList);
            lst.setAdapter(adapter);
            btn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = ed.getText().toString();
                    helper.addTodo(title);
                    reloadData();
                }
            });

            lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Todo todo = (Todo) parent.getItemAtPosition(position);
                    Log.v("todo", "got todo: " + todo.title + " - " + todo.checked);

                    helper.updateTodo(todo);
                    reloadData();
                }
            });

            lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Todo todo = (Todo) parent.getItemAtPosition(position);
                    Log.v("todo", "got todo: " + todo.title + " - " + todo.checked);

                    helper.deleteTodo(todo);
                    reloadData();
                    return true;
                }
            });

            return rootView;
        }

        public void reloadData() {

            todoList.clear();
            todoList.addAll(helper.getAll());
            adapter.notifyDataSetChanged();

        }
    }

}
