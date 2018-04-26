package tw.org.iii.travelapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private ListView profile_list;
    private String [] data1, data2;
    private MyAdapter myAdapter;
    private TextView item_content;
    private View item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("個人資料");
        profile_list = findViewById(R.id.profile_list);

        init();
    }

    private void init(){
        data1 = new String[]{"帳號", "密碼"};
        data2 = new String[]{"0912345678", "********"};
        myAdapter = new MyAdapter(ProfileActivity.this);
        profile_list.setAdapter(myAdapter);
        profile_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        profile_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:

                        break;
                    case 1:
                        renewPassword();
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                }
            }
        });
    }
    //修改密碼
    private void renewPassword(){
        item = LayoutInflater.from(
                ProfileActivity.this).inflate(R.layout.layout_dialog_password, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setView(item)
                .setTitle("修改密碼")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = item.findViewById(R.id.editText_password);
                        String newPassword = editText.getText().toString();
                        data2[1] = newPassword;
                        myAdapter.notifyDataSetChanged();
                    }
                }).show();
    }

    //BaseAdapter for ListView
    private class MyAdapter extends BaseAdapter{

        private Context myContext;
        private LayoutInflater inflater;

        public MyAdapter(Context context){
            this.myContext = context;
            inflater = LayoutInflater.from(this.myContext);
        }
        //取得list的數量
        @Override
        public int getCount() {
            return data1.length;
        }

        @Override
        public Object getItem(int position) {
            return data1[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //設置ListView的layout,沒有根目錄root值為null
            convertView = inflater.inflate(R.layout.layout_profile_item, null);
            //取得ListView layout的每個view
            TextView item_title = convertView.findViewById(R.id.item_title);
            item_content = convertView.findViewById(R.id.item_content);
            //給值
            item_title.setText(data1[position]);
            item_content.setText(data2[position]);
            //回傳convertView
            return convertView;
        }
    }
}