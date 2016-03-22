import java.util.ArrayList;
import java.util.List;

/**
 * Created by chae on 3/15/2016.
 */
public class MakeCombination {
    public void makeCombination(int in) {
        List<String> strs = new ArrayList<>() ;
        generateStr(in, 1, "", strs) ;
        for(String str : strs) {
            System.out.println(str);
        }
    }

    public void generateStr(int in, int len, String str, List<String> strs) {
        int[] nums = makeArray(in) ;
        int n = makeNum(nums, 0, len) ;

        if(n > 26 ) return ;

        if(nums.length > len) {
            String generated = str + makeAlpha(n) ;

            int next_in = makeNum(nums, 1, nums.length) ;

            while(nums.length >= len) {
                generateStr(next_in, len, generated, strs);

                generated = str ;
                next_in = in ;
                len ++ ;
            }
        } else {
            strs.add(str + makeAlpha(n)) ;
        }

    }

    private int[] makeArray(int in) {
        int[] arr = new int[String.valueOf(in).length()] ;

        for(int i = String.valueOf(in).length() - 1 ; i >= 0 ; i--) {
            arr[i] = in % 10 ;
            in = in / 10 ;
        }

        return arr ;
    }

    private int makeNum(int[] nums, int start, int end) {
        String val = "" ;
        for(int i = start ; i < end; i++) {
            val = val + String.valueOf(nums[i]) ;
        }

        return Integer.parseInt(val) ;
    }

    private char makeAlpha(int in) {
        if (in == 0) return 0 ;
        else return (char) ('A' +  (in - 1)) ;
    }

    public static void main(String[] args) {
        MakeCombination combination = new MakeCombination() ;
        combination.makeCombination(1123);
    }
}
