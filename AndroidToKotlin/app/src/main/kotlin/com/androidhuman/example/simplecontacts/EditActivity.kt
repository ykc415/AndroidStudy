package com.androidhuman.example.simplecontacts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.androidhuman.example.simplecontacts.model.Person
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.menu_activity_edit_done == item.itemId) {
            applyChanges()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun applyChanges() {
        if (et_activity_edit_name.text.isEmpty()) {
            // TODO: Remove error on content changes
            ti_activity_edit_name.error = getText(R.string.msg_name_cannot_be_empty)
            return
        }

        val realm = Realm.getDefaultInstance()

        // Get next id value for primary key
        val currentMaxId = realm.where(Person::class.java).max(Person.PRIMARY_KEY)
        val nextId: Long = if (null == currentMaxId) 0L else currentMaxId.toLong() + 1

        realm.beginTransaction()

        realm.createObject(Person::class.java, nextId).run {
            name = et_activity_edit_name.text.toString()
            address = et_activity_edit_address.text.toString()
        }

        /* Alternative method:
        Person person = new Person();
        person.setId(nextId);
        person.setName(etName.getText().toString());
        person.setAddress(etAddress.getText().toString());
        realm.copyToRealm(person);
        */

        realm.commitTransaction()

        realm.close()

        setResult(RESULT_OK)
        finish()
    }

    companion object {

        val REQUEST_CODE = 10
    }
}
