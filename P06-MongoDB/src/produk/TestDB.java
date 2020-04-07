/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produk;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.UpdateResult;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Ade Sug
 */
public class TestDB {
    public static void main(String[] args) {
        try {
            //Koneksi database
            MongoDatabase database = Koneksi.sambungDB();
            
            //melihat daftar kolekksi (tabel)
            System.out.println("============================");
            System.out.println("Daftar Tabel dalam Database");
            MongoIterable<String> tables = database.listCollectionNames();
            for (String name : tables){
                System.out.println(name);
            }
            
            //menambah data
            System.out.println("============================");
            System.out.println("Menambah data");
            MongoCollection<Document> col = database.getCollection("produk");
            Document doc = new Document();
            doc.put("nama", "Printer InkJet");
            doc.put("harga", 1204000);
            doc.put("tanggal", new Date());
            col.insertOne(doc);
            System.out.println("Data telah disimpan dalam koleksi");
            
            //mendapatkan _id dari dokumen yang baru saja diinsery
            ObjectId id = new ObjectId(doc.get("_id").toString());
            
            //melihat/menampilkan data
            System.out.println("============================");
            System.out.println("Data dalam koleksi produk");
            MongoCursor<Document> cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
            
            //mencari dokumen berdasarkan id
            Document myDoc = col.find(eq("_id", id)).first();
            System.out.println("============================");
            System.out.println("Pencarian data berdasarkan id : "+id);
            System.out.println(myDoc.toJson());
            
            //mengedit data
            Document docs = new Document();
            docs.put("nama", "Canon");
            Document doc_edit = new Document("$set", docs);
            UpdateResult hasil_edit = col.updateOne(eq("_id", id), doc_edit);
            System.out.println(hasil_edit.getModifiedCount());
            
            //melihat/menampilkan data
            System.out.println("============================");
            System.out.println("Data dalam koleksi produk");
            cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
            
            //menghapus data
            col.deleteOne(eq("_id",id));
            
            //melihat/menampilkan data
            System.out.println("============================");
            System.out.println("Data dalam koleksi produk");
            cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } catch (Exception e) {
            System.out.println("Error : "+e.getMessage());
        }
    }
    
}
