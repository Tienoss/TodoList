package epsi.fr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

import epsi.fr.model.Todo;

/**
 * Created by Etienne and Maxime on 17/05/14.
 */
public class TodoAdapter extends BaseAdapter {
    List<Todo> data;
    Context context;

    public TodoAdapter(Context _context, List<Todo> _data) {
        context = _context;
        data    = _data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int pos) {
        return data.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    /*static class ViewHolder {
        CheckBox titleView;

    }
*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*ViewHolder holder;
        if(convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.todo_item, parent, false);
            holder = new ViewHolder();
            holder.titleView    = (CheckBox) convertView.findViewById(R.id.todoItemTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Todo todo = data.get(position);
        holder.titleView.setText(todo.title);
        return convertView;*/

        CheckBox chk = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todo_item,
                    parent, false);
            chk = (CheckBox) convertView.findViewById(R.id.check);
            convertView.setTag(chk);

            chk.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Todo changeTask = (Todo) cb.getTag();
                    changeTask.checked = (cb.isChecked() == true ? 1 : 0);
                }
            });

        } else {
            chk = (CheckBox) convertView.getTag();
        }
        Todo current = data.get(position);
        chk.setText(current.title);
        chk.setChecked(current.checked == 1 ? true : false);
        chk.setTag(current);
        return convertView;
    }
}
