import com.opencsv.CSVReader;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private Text word = new Text();
    private final int COUNTRY_COL_INDEX = 7;

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        word.set("Compteurs_vente");
        String line = value.toString();
        String[] values = line.split(",");
        if (!values[0].equals("Transaction_date")) {
                //&& (values[COUNTRY_COL_INDEX].length() == 0)) {
            context.write(word, new IntWritable(values[COUNTRY_COL_INDEX].length()));
        }
    }
}