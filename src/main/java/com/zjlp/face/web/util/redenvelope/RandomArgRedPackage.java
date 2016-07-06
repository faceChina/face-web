package com.zjlp.face.web.util.redenvelope;

import java.util.Arrays;
import java.util.Random;


public class RandomArgRedPackage {
	static Random random = new Random();
	static {
		random.setSeed(System.currentTimeMillis());
	}

	/**
	 * 红包拆分工具类 使用时注意拆分数量count不能大于total*100 使用事例
	 * RandomArgRedPackage.acquireSmailRedPackageList(500, 5, 1, 3);
	 * 
	 * @param total
	 *            (红包金额)
	 * @param count
	 *            (拆包数量)
	 * @param min
	 *            (最小拆分金额)
	 * @param max
	 *            (最大拆分金额)
	 * @return 拆分后的小红包数组
	 * @throws Exception
	 */
	public static long[] acquireSmailRedPackageList(long total, int count)
			throws Exception {
		if (count > total) {
			throw new Exception("拆分数量count不能大于total");
		}
		if (count <= 1) {
			throw new Exception("拆分数量必须大于1");
		}
		long realMax = (long) ((total / count) * 3);
		long[] split = new long[count];
		long number = (long) (total);
		java.util.Random random=new Random();
		int  rate=random.nextInt(300);
		if(rate==0){
			rate=1;
		}
		long realMin = (total / count) / rate;
		if(realMin==0)
		{
			realMin=1;
		}
/*		System.out.println("最大红包:"+realMax);
		System.out.println("最小红包:"+realMin);*/
		long[] result = generate(number, count, realMax, realMin);
		for (int i = 0; i < result.length; i++) {
			split[i] = result[i];
		}
		return split;
	}

	public static void main(String[] args) {
		try {
			for (int i =2; i <100; i++) {
				long[] list = acquireSmailRedPackageList(25600L,99);
				System.out.println("总金额：" + 20000 + "拆分红包第:" + i + "次    "
						+ Arrays.toString(list));
				int mm=0;
				for(int j=0;j<list.length;j++)
				{
					mm+=list[j];
				}
				System.out.println("M:"+mm);
			}
			// System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生产min和max之间的随机数，但是概率不是平均的，从min到max方向概率逐渐加大。
	 * 先平方，然后产生一个平方值范围内的随机数，再开方，这样就产生了一种“膨胀”再“收缩”的效果。
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	static long xRandom(long min, long max) {
		if (max - min <= 0) {
			return 0;
		} else {
			return sqrt(nextLong(sqr(max - min)));
		}
	}

	/**
	 * 
	 * @param total
	 *            红包总额
	 * @param count
	 *            红包个数
	 * @param max
	 *            每个小红包的最大额
	 * @param min
	 *            每个小红包的最小额
	 * @return 存放生成的每个小红包的值的数组
	 */
	public static long[] generate(long total, int count, long max, long min) {

		if (count * max < total) {
			System.out.println("最大红包钱数 * 红包个数 < 总钱数");
			System.exit(-1);
		}

		long[] result = new long[count];

		long average = total / count;

//		long a = average - min;
//		long b = max - min;

		//
		// 这样的随机数的概率实际改变了，产生大数的可能性要比产生小数的概率要小。
		// 这样就实现了大部分红包的值在平均数附近。大红包和小红包比较少。
//		long range1 = sqr(average - min);
//		long range2 = sqr(max - average);

		for (int i = 0; i < result.length;i++) {
			// 因为小红包的数量通常是要比大红包的数量要多的，因为这里的概率要调换过来。
			// 当随机数>平均值，则产生小红包
			// 当随机数<平均值，则产生大红包
			if (nextLong(min, max) > average) {
				// 在平均线上减钱
				// long temp = min + sqrt(nextLong(range1));
				long temp = min + xRandom(min, average);
				if(i>0)
				{
				  if(temp!=result[i-1])
				  {
						result[i] = temp;
						total -= temp;  
				  }
				}else{
					result[i] = temp;
					total -= temp;
				}

			} else {
				// 在平均线上加钱
				// long temp = max - sqrt(nextLong(range2));
				long temp = max - xRandom(average, max);			
				if(i>0)
				{
				  if(temp!=result[i-1])
				  {
						result[i] = temp;
						total -= temp;  
				  }
				}else{
					result[i] = temp;
					total -= temp;
				}			
				
	
			}
		}
		// 如果还有余钱，则尝试加到小红包里，如果加不进去，则尝试下一个。
		while (total > 0) {
			for (int i = 0; i < result.length; i++) {
				if (total > 0 && result[i] < max) {
					result[i]++;
					total--;
				}
			}
		}
		// 如果钱是负数了，还得从已生成的小红包中抽取回来
		while (total < 0) {
			for (int i = 1; i < result.length;i++) {
				if (total < 0 && result[i] > min) {
				    if((result[i]-1)!=result[i-1])
				    {
					result[i]--;
					total++;
				    }
					
				}
			}
		}
		return result;
	}

	static long sqrt(long n) {
		// 改进为查表？
		return (long) Math.sqrt(n);
	}

	static long sqr(long n) {
		// 查表快，还是直接算快？
		return n * n;
	}

	static long nextLong(long n) {
		return random.nextInt((int) n);
	}

	static long nextLong(long min, long max) {
		return random.nextInt((int) (max - min + 1)) + min;
	}
}