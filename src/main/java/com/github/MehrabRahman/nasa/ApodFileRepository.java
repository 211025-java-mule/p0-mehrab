package com.github.MehrabRahman.nasa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ApodFileRepository implements ApodRepository{
    public void create(Apod output) {
        //File
		File outputFile = new File("output.txt");
		try(FileWriter outpuFileWriter = new FileWriter(outputFile, true);	) {
			outpuFileWriter.write(output.toString() + "\n");
		} catch (IOException e) {
			// log.error("Output file error");
		}
    }
}
