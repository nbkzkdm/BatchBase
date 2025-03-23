package jp.co.kdm.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


public class FileUtil {

    /**
     * .gz ファイルを一時ファイルに解凍する
     */
    public static File decompressGzipToTempFile(File gzFile) throws IOException {
        File tempFile = File.createTempFile("decompressed-", ".tmp");
        tempFile.deleteOnExit();

        try (GZIPInputStream gzipIn = new GZIPInputStream(new FileInputStream(gzFile));
             FileOutputStream out = new FileOutputStream(tempFile)) {

            byte[] buffer = new byte[8192];
            int len;
            while ((len = gzipIn.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }

        return tempFile;
    }

    /**
     * 解凍されたCSVファイルを1行ずつ読み込む
     */
    public static List<String[]> readCsv(File csvFile, Charset charset) throws IOException {
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(csvFile.toPath(), charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                // カンマ区切りで分割（必要に応じてCSVパーサーに変更可能）
                String[] cols = line.split(",", -1);
                lines.add(cols);
            }
        }

        return lines;
    }

    /**
     * XMLファイルをJavaオブジェクトに変換（JAXB使用）
     */
    public static <T> T readXml(File xmlFile, Class<T> clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        @SuppressWarnings("unchecked")
        T obj = (T) unmarshaller.unmarshal(xmlFile);
        return obj;
    }
}

