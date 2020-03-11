package com.rk.utils.office;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Excel文件解析处理
 *
 * @author zk
 */
public class ExcelUtil {

    /**
     * Excel解析入口
     *
     * @param is   excel流
     * @param xlsx 是否为xlsx文件
     * @return
     */
    public static List<String> parseStringExcel(InputStream is, boolean xlsx) {
        Workbook wb = null;
        try {
            if (xlsx) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = new HSSFWorkbook(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Sheet sheet;
        Row row;
        Cell cell;
        String value;
        List<String> datas = new ArrayList<>();
        StringBuffer line = new StringBuffer();
        int pRowNum = 0;
        DecimalFormat df = new DecimalFormat("0.00000000");
        boolean numberFlag = false;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            sheet = wb.getSheetAt(i);
            pRowNum = sheet.getPhysicalNumberOfRows();
            for (int j = 0; i < sheet.getLastRowNum(); j++) {
                line.delete(0, line.length());
                if (pRowNum <= 0) {
                    break;
                }
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                pRowNum--;

                for (int k = 0; k < row.getLastCellNum(); k++) {
                    numberFlag = false;
                    line.append(",");
                    value = "";
                    cell = row.getCell(k);
                    if (cell == null) {
                        continue;
                    }
                    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        numberFlag = true;
                        value = String.valueOf(cell.getNumericCellValue());
                    } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        value = cell.getStringCellValue();
                    } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                        try {
                            value = String.valueOf(cell.getNumericCellValue());
                            numberFlag = true;
                        } catch (Exception e) {
                            value = cell.getStringCellValue();
                        }
                    }
                    if (numberFlag) {
                        if (value.contains("E")) {
                            value = String.valueOf(df.format(cell.getNumericCellValue()));
                            if (value.endsWith(".00000000")) {
                                value = value.replace(".00000000", "");
                            }
                        }
                    }
                    line.append(value);
                }
                datas.add(line.toString().substring(1));
            }
        }
        return datas;
    }

    /**
     * Excel解析入口
     *
     * @param is   excel流
     * @param xlsx 是否为xlsx文件
     * @return
     */
    public static List<List<Object>> parseExcel(InputStream is, boolean xlsx) {
        Workbook wb = null;
        try {
            if (xlsx) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = new HSSFWorkbook(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Sheet sheet;
        Row row;
        Cell cell;
        List<List<Object>> datas = new ArrayList<>();
        List<Object> data;
        int pRowNum = 0;
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            sheet = wb.getSheetAt(i);
            pRowNum = sheet.getPhysicalNumberOfRows();
            for (int j = 0; i < sheet.getLastRowNum(); j++) {
                if (pRowNum <= 0) {
                    break;
                }
                row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                pRowNum--;

                data = new ArrayList<>();
                for (int k = 0; k < row.getLastCellNum(); k++) {
                    cell = row.getCell(k);
                    data.add(getValue(cell));
                }
                datas.add(data);
            }
        }
        return datas;
    }

    public static boolean makeExcel(String xlsFile, boolean xlsx, List<List<String>> datas) {
        Workbook wb = null;
        try {
            File file = new File(xlsFile);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(xlsFile);
                if (xlsx) {
                    wb = new XSSFWorkbook(fis);
                } else {
                    wb = new HSSFWorkbook(fis);
                }
                fis.close();
            } else {
                if (xlsx) {
                    wb = new XSSFWorkbook();
                } else {
                    wb = new HSSFWorkbook();
                }
            }
            int sheetIndex = wb.getNumberOfSheets();
            Sheet sheet = null;
            if (sheetIndex == 0) {
                sheet = wb.createSheet();
            } else {
                sheet = wb.getSheetAt(0);
            }
            int rowIndex = sheet.getLastRowNum();
            if (rowIndex != 0) {
                rowIndex += 1;
            }
            Row row;
            Cell cell;
            for (List<String> data : datas) {
                row = sheet.createRow(rowIndex);
                for (int i = 0; i < data.size(); i++) {
                    cell = row.createCell(i);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(data.get(i));
                }
                rowIndex++;
            }
            FileOutputStream fos = new FileOutputStream(xlsFile);
            fos.flush();
            wb.write(fos);
            wb.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xlsx;
    }

    public static boolean makeExcelFromCSV(String csvFile, String xlsFile, String fixChar, boolean xlsx,
                                           String charset) {
        Workbook wb = null;
        FileOutputStream fos = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), charset));
            if (xlsx) {
                wb = new XSSFWorkbook();
            } else {
                wb = new HSSFWorkbook();
            }
            Sheet sheet = wb.createSheet();

            int rowIndex = 0;
            Row row;
            Cell cell;
            String line;
            String columns[];
            while ((line = br.readLine()) != null) {
                columns = line.split(fixChar);
                row = sheet.createRow(rowIndex);
                for (int i = 0; i < columns.length; i++) {
                    cell = row.createCell(i);
                    cell.setCellValue(columns[i].replace("\"", ""));
                }
                rowIndex++;
            }
            fos = new FileOutputStream(xlsFile);
            wb.write(fos);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return xlsx;
    }

    public static boolean makeExcel(String xlsFile, boolean xlsx, List<Object[]> datas, int[] dataType) {
        Workbook wb = null;
        try {
            File file = new File(xlsFile);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(xlsFile);
                if (xlsx) {
                    wb = new XSSFWorkbook(fis);
                } else {
                    wb = new HSSFWorkbook(fis);
                }
                // wb = new SXSSFWorkbook(new XSSFWorkbook(fis), 1000);
                fis.close();
            } else {
                if (xlsx) {
                    wb = new XSSFWorkbook();
                } else {
                    wb = new HSSFWorkbook();
                }
                // wb = new SXSSFWorkbook(new XSSFWorkbook(), 1000);
            }
            int sheetIndex = wb.getNumberOfSheets();
            Sheet sheet = null;
            if (sheetIndex == 0) {
                sheet = wb.createSheet();
            } else {
                sheet = wb.getSheetAt(sheetIndex - 1);
            }
            int rowIndex = sheet.getLastRowNum();
            if (sheetIndex == 0) {
            } else {
                rowIndex += 1;
            }

            Row row;
            Cell cell;

            // 定义Cell格式
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper creationHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd  hh:mm:ss"));

            for (Object[] data : datas) {
                row = sheet.createRow(rowIndex);
                for (int i = 0; i < dataType.length; i++) {
                    cell = row.createCell(i);
                    if (dataType[i] == Types.VARCHAR) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        // cell.setCellValue(String.valueOf(data[i]));
                        cell.setCellValue((String) data[i]);
                    } else if (dataType[i] == Types.NUMERIC) {
                        if (data[i] instanceof String) {
                            nullVal(cell, data[i]);
                        } else {
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellValue((double) data[i]);
                        }
                    } else if (dataType[i] == Types.DATE || dataType[i] == Types.TIMESTAMP) {
                        if (data[i] instanceof String) {
                            nullVal(cell, data[i]);
                        } else {
                            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                            cell.setCellValue((Date) data[i]);
                        }
                        cell.setCellStyle(cellStyle);
                    }
                }
                rowIndex++;
            }
            FileOutputStream fos = new FileOutputStream(xlsFile);
            fos.flush();
            wb.write(fos);
            wb.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> loadCsv(InputStream inputStream, String charset) throws IOException {
        List<String> fileDatas = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            fileDatas.add(line);
        }
        return fileDatas;
    }

    public static List<List<Object>> loadCsvTable(InputStream inputStream, String charset) throws IOException {
        List<List<Object>> fileDatas = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
        String line;
        List<Object> lineList;
        while ((line = bufferedReader.readLine()) != null) {
            lineList = new ArrayList<>();
            for (String s : line.split(",")) {
                lineList.add(s);
            }
            fileDatas.add(lineList);
        }
        return fileDatas;
    }

    private static void nullVal(Cell cell, Object val) {
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue((String) val);
    }

    private static Object getValue(Cell cell) {
//        DecimalFormat df = new DecimalFormat("0.00000000");
        Object value = null;
        boolean flag = false;
        if (cell != null) {
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                value = cell.getNumericCellValue();
                flag = true;
            } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                value = cell.getStringCellValue();
            } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                try {
                    value = cell.getNumericCellValue();
                } catch (Exception e) {
                    value = cell.getStringCellValue();
                    flag = true;
                }
            }
            if (flag && DateUtil.isCellDateFormatted(cell)) {
                Calendar calendar = new GregorianCalendar(1900, 0, -1);
                Date d = calendar.getTime();
                calendar.add(Calendar.DAY_OF_YEAR, ((Double) value).intValue());
                value = calendar.getTime();
            }
        }
        return value;
    }

}
