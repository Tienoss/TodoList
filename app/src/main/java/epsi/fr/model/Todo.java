package epsi.fr.model;

/**
 * Created by Etienne on 17/05/14.
 */
public class Todo {
    public int todo_id;
    public String title;
    public int checked;

    public Todo(int _id, String _title, int _checked){
        todo_id = _id;
        title = _title;
        checked = _checked;
    }
}
