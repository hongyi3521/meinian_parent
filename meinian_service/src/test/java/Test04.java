import com.alibaba.excel.EasyExcel;
import com.hongyi.listener.ExcelListener;
import com.hongyi.vo.ExcelData;
import org.junit.Test;

public class Test04 {
    @Test
    public void run(){
        String fileName = "D:\\qiniu\\ordersetting_template.xlsx";
        EasyExcel.read(fileName, ExcelData.class,new ExcelListener()).sheet().doRead();
    }
}
