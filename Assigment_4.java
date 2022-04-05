import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Assigment_4 {

	public static void main(String[] args) throws ParseException, IOException {
//		Scanner in = new Scanner(System.in);

		ArrayList<ArrayList<String>> finalList = new ArrayList<ArrayList<String>>();
		// getting the donors List with name , bloodtype and complete list
		List<String> ListofD = new ArrayList<String>();
		List<String> donorsbloodList = new ArrayList<String>();
		ArrayList<String> donorname = new ArrayList<>();
		List<Integer> MatchesR = new ArrayList<Integer>();
		List<Integer> MatchesD = new ArrayList<Integer>();
		int dayPlus = 0;
		getDonors(ListofD, donorsbloodList, donorname);
		// same with reciepents
		List<String> ListofR = new ArrayList<String>();
		List<String> recipientsbloodList = new ArrayList<String>();
		ArrayList<String> recipientsname = new ArrayList<>();
		getReciepents(ListofR, recipientsbloodList, recipientsname);
//		 System.out.println("Doners" + donorsbloodList);
//		 System.out.println("Recipients" + recipientsbloodList);
//		bloodtypeMatch(donorsbloodList.get(25), recipientsbloodList.get(51));
		possibilities(recipientsbloodList, donorsbloodList, MatchesR, MatchesD);
//		System.out.println("Doners " + MatchesD);
//		System.out.println("Reciepents " + MatchesR);
		// getting dates
		List<String> donorSame = new ArrayList<String>();
		datePairs(MatchesR, MatchesD, recipientsbloodList, recipientsname, donorsbloodList, donorname, finalList,
				dayPlus, donorSame);
		getPrinted(finalList, recipientsbloodList);

	}

	private static void printinFile(String printString) throws IOException, ParseException {
		int i;
		List<String> ls = new ArrayList<String>();
		for (i = 0; i < 1; i++) {
			String str = null;
			str = printString;
			ls.add(str);
		}
		String listString = "";
		for (String s : ls) {
			listString += s + "\n";
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter("appointments.txt");
			writer.write(listString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//sorting it by days of appoitment
	private static void getPrinted(ArrayList<ArrayList<String>> finalList, List<String> recipientsbloodList)
			throws ParseException, IOException {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<String>> updatefinalList = new ArrayList<ArrayList<String>>();
		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		// IMPORTANT: Save the old System.out!
		PrintStream old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
		for (int i = 0; i < finalList.get(0).size(); i++) {
			updatefinalList.addAll(finalList);
		}
		List<String> Samedonors = new ArrayList<String>();
		List<Integer> indexes = new ArrayList<Integer>();
//	       sortDates(finalList.get(4));

		int size = recipientsbloodList.size();
//		System.out.println(finalList.get(0).indexOf("Teddy O'Connor"));
		while (size != 0) {
//	       System.out.println(sortDates(finalList.get(4)));
			int i;
			for (i = 0; i < size; i++) {
//	    	    System.out.println("MY SIZE " + size + "    i    " + i);
//				System.out.println("SIZE " + updatefinalList.get(2).size());
				if (!Samedonors.contains(updatefinalList.get(2).get(i))) {
					System.out.println("-------------------------------------------------------------------");
					// Put things back
					System.out.println(String.format("%-40s %s", "     Recipient: " + updatefinalList.get(0).get(i),
							" Blood Type: " + updatefinalList.get(1).get(i)));
					System.out.println(String.format("%-40s %s", "     Donor    : " + updatefinalList.get(2).get(i),
							" Blood Type: " + updatefinalList.get(3).get(i)));
					System.out.println("     Date     : " + updatefinalList.get(4).get(i));
					Samedonors.add(updatefinalList.get(2).get(i));
					indexes.add(i);
				}
			}
//	       System.out.println(indexes);
			removePrinted(indexes, recipientsbloodList, finalList, updatefinalList, size);
			Samedonors.clear();
			indexes.clear();
			i = 0;
			size = updatefinalList.get(0).size();
		}
		System.out.println("-------------------------------------------------------------------");
		// Putting system out..... things back
		System.out.flush();
		System.setOut(old);
		// printing everything to file
		printinFile(baos.toString());
		// printing everything to console
		System.out.println(baos.toString());
//			  System.out.println("LIST " + finalList.get(4));
	}

	private static void removePrinted(List<Integer> indexes, List<String> recipientsbloodList,
			ArrayList<ArrayList<String>> finalList, ArrayList<ArrayList<String>> updatefinalList, int size) {
		// TODO Auto-generated method stub
		int indexcount = 0;
		size = recipientsbloodList.size();
//	System.out.println(finalList.get(0));
//	System.out.println("SIZE " + finalList.get(0).size());
		for (int i = 0; i < size; i++) {
			if (indexes.size() == 0) {
				break;
			}
			if (i == indexes.get(indexcount)) {
//			System.out.println("Match at index  " + i   +  " original index " + indexes.get(indexcount));
				finalList.get(0).set(i, null);
				finalList.get(1).set(i, null);
				finalList.get(2).set(i, null);
				finalList.get(3).set(i, null);
				finalList.get(4).set(i, null);
//			System.out.println("INDEX " + i)
				if (indexcount < (indexes.size() - 1)) {
					indexcount++;
				}
			}
		}
		finalList.get(0).removeAll(Collections.singleton(null));
		finalList.get(1).removeAll(Collections.singleton(null));
		finalList.get(2).removeAll(Collections.singleton(null));
		finalList.get(3).removeAll(Collections.singleton(null));
		finalList.get(4).removeAll(Collections.singleton(null));
		for (int i = 0; i < finalList.get(0).size(); i++) {
			updatefinalList.addAll(finalList);
		}
//System.out.println("SIZE " + updatefinalList.get(0).size());
		size = (size - updatefinalList.get(0).size());
//	System.out.println(updatefinalList.get(0));
//	System.out.println("SIZE " + updatefinalList.get(0).size());
	}

	private static void datePairs(List<Integer> MatchesR, List<Integer> MatchesD, List<String> recipientsbloodList,
			ArrayList<String> recipientsname, List<String> donorsbloodList, ArrayList<String> donorname,
			ArrayList<ArrayList<String>> finalList, int dayPlus, List<String> donorSame) throws ParseException {
		// TODO Auto-generated method stub
		List<String> upDatedDonor = new ArrayList<String>();
		List<String> upDatedRec = new ArrayList<String>();
		List<String> bloodDon = new ArrayList<String>();
		List<String> bloodRec = new ArrayList<String>();
		for (int i = 0; i < recipientsname.size(); i++) {
			upDatedDonor.add("");
			upDatedRec.add("");
			bloodDon.add("");
			bloodRec.add("");
		}

		int n;
//		System.out.println(donorsbloodList);
		for (int i = 0; i < MatchesR.size(); i++) {
			n = MatchesD.get(i);
			upDatedDonor.set(i, donorname.get(n));
			bloodDon.set(i, donorsbloodList.get(n));
//			System.out.println("INDEX : " + n + "NAME " + donorname.get(n));
		}
//		System.out.println("IP" +bloodDon);
		for (int i = 0; i < MatchesR.size(); i++) {
			n = MatchesR.get(i);
			upDatedRec.set(i, recipientsname.get(n));
			bloodRec.set(i, recipientsbloodList.get(n));
//			System.out.println("INDEX : " + n + "NAME " + donorname.get(n));
		}
//		System.out.println(upDatedDonor.size());
//		System.out.println(" UPDATE------: " + upDatedDonor);
//		System.out.println(" OLD------: " + recipientsname);
//		System.out.println(" UPDATE------: " + upDatedRec);
		// matching
		finalList.add(0, (ArrayList<String>) upDatedRec);
		finalList.add(1, (ArrayList<String>) bloodRec);
		finalList.add(2, (ArrayList<String>) upDatedDonor);
		finalList.add(3, (ArrayList<String>) bloodDon);

//		System.out.println(finalList);
//		System.out.println(finalblood);

//date of appoitment
		donorSame = new ArrayList<String>();
		List<String> dateAppoitment = new ArrayList<String>();
		int totalSize;
		if (recipientsname.size() > donorname.size()) {
			totalSize = recipientsname.size();
		} else {
			totalSize = donorname.size();
		}
		for (int size = 0; size < totalSize; size++) {
			dateAppoitment.add("");
		}
		boolean same = true;
		int counting = 0;
		for (int i = 0; i < totalSize; i++) {
//			System.out.println("ROUND             " +i);
//			System.out.println("-------------------------------------------------------------------");
			if (!donorSame.contains(finalList.get(2).get(i))) {
//			System. out.println("DAYYYYYYY    " + dayPlus + "integer " + round++);
				donorSame.add(finalList.get(2).get(i));
//				System.out.println(String.format("%-40s %s", "     Recipient: " + finalList.get(0).get(i),
//						" Blood Type: " + finalList.get(1).get(i)));
//			finalList.remove((0).get(i));
//				System.out.println(String.format("%-40s %s", "     Donor    : " + finalList.get(2).get(i),
//						" Blood Type: " + finalList.get(3).get(i)));
//			gettheDate(dayPlus, dateAppoitment, i);
				counting++;
				if (counting > 12) {
					dayPlus++;
					counting = 1;
//					System.out.println(counting.size());
				}
				same = false;
				// gettheDate(dayPlus, dateAppoitment, i);
			} else if (donorSame.contains(finalList.get(2).get(i))) {
//				System.out.println(indexofMatch);
//				System.out.println(String.format("%-40s %s", "     Recipient: " + finalList.get(0).get(i),
//						" Blood Type: " + finalList.get(1).get(i)));
//				System.out.println(String.format("%-40s %s", "     Donor    : " + finalList.get(2).get(i),
//						" Blood Type: " + finalList.get(3).get(i)));
				same = true;
//				dateAppoitment.set(, null)
				donorSame.add(finalList.get(2).get(i));
			}
			gettheDate(dayPlus, dateAppoitment, i, donorSame, finalList, same);
		}
//		System.out.println("-------------------------------------------------------------------");
		finalList.add(4, (ArrayList<String>) dateAppoitment);
//		System.out.println(sortDates(dateAppoitment));

	}

	private static ArrayList<String> sortDates(List<String> dateAppoitment) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy");
		Map<Date, String> dateFormatMap = new TreeMap<>();
		for (String date : dateAppoitment)
			dateFormatMap.put(f.parse(date), date);
		return new ArrayList<>(dateFormatMap.values());
	}

//skipping saturdays and sundays
	public static LocalDate addDaysSkippingWeekends(LocalDate date, int days) {
		LocalDate result = date;
		int addedDays = 0;
		while (addedDays < days) {
			result = result.plusDays(1);
			if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
				++addedDays;
			}
		}
		return result;
	}

//getting the day
	private static void gettheDate(int dayPlus, List<String> dateAppoitment, int countDAY, List<String> donorSame,
			ArrayList<ArrayList<String>> finalList, boolean same) throws ParseException {
		// TODO Auto-generated method stub
		Date today;
		String dateOut;
		Date currentDay;
		String nextDay;
		DateFormat formatter;
		Locale currentLocale = new Locale("en", "IE");
		formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, currentLocale);
		today = new Date();
		dateOut = formatter.format(today);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, dayPlus);
		int plusDates = 56;
		currentDay = convertToDate(addDaysSkippingWeekends(convertToLocalDateViaInstant(today), dayPlus));
		if (same) {
			// getting the frequency of the repeating.....if same it have to be at least 2
			// times the name in the list
			int repeat = Collections.frequency(donorSame, donorSame.get(countDAY));
//				System.out.println("MORE THAN 1     " + repeat);
//				 and getting correct  count of days as D have to wait 56 days and  multipliing by number repeats in list -1 ..
			plusDates = plusDates * (repeat - 1);
			int indexofMatch = donorSame.indexOf(finalList.get(2).get(countDAY));
			DateFormat dayfromlist = new SimpleDateFormat("d MMM YYYY", Locale.ENGLISH);
			Date date = formatter.parse(dateAppoitment.get(indexofMatch));
			cal.setTime(date);
			// because my previus date is set without weekends.... i dont have to write
			// condition for weekend...
			cal.add(Calendar.DATE, plusDates);
			date = cal.getTime();
//			donormatchDay = convertToDate(addDaysSkippingWeekends(convertToLocalDateViaInstant(date), plusDates));
			String printdate = dayfromlist.format(date);
//			System.out.println("MATCH " + indexofMatch + "  MY NEW DAY    " + date);
//			System.out.println("     Date     : " + printdate);
			// adding day to list
			dateAppoitment.set(countDAY, printdate);
		} else {
//formating it back do the date
			nextDay = formatter.format(currentDay);
//			System.out.println("     Date     : " + nextDay);
			dateAppoitment.set(countDAY, nextDay);
//		System.out.println("TODAY" + dateAppoitment.set(countDAY, nextDay));
			if (dateAppoitment.contains(dateAppoitment.get(countDAY))) {
//			System.out.println("Freq of matches : "+Collections.frequency(dateAppoitment, dateAppoitment.get(i)));
//freuency of the same date being in m list of days to have 12 appoitments in a day,( not nessesary have to use on the names what repeating...)
//	int frequency = Collections.frequency(dateAppoitment, dateAppoitment.get(countDAY));
//	System.out.println(frequency + " Frequency " +  dateAppoitment.get(countDAY));
//	if (frequency > 11) {
//		System.out.println("NEED A DAY");
//	}
			}
		}

//		System.out.println(dateOut + "----- " + nextDay);
	}

//changind Date to LocalDate
	public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static Date convertToDate(LocalDate dateToConvert) {
		return java.sql.Date.valueOf(dateToConvert);
	}

//all the posibilities of donors and reciepents
	private static void possibilities(List<String> recipientsbloodList, List<String> donorsbloodList,
			List<Integer> MatchesR, List<Integer> MatchesD) {
		// TODO Auto-generated method stub
		// System.out.println(recipientsbloodList.size());
		boolean[][] posible = new boolean[recipientsbloodList.size()][donorsbloodList.size()];
		ArrayList<ArrayList<String>> pos = new ArrayList<ArrayList<String>>();
		// adding size to my 2D array
		for (int j = 0; j < recipientsbloodList.size(); j++) {
			pos.add(new ArrayList<String>());
		}
		int don;
		int rec;

		for (rec = 0; rec < recipientsbloodList.size(); rec++) {
			for (don = 0; don < donorsbloodList.size(); don++) {

				if (bloodtypeMatch(recipientsbloodList.get(rec), donorsbloodList.get(don))) {
					posible[rec][don] = true;
					// adding value true or false
					pos.get(rec).add("true");
				} else {
					posible[rec][don] = false;
					pos.get(rec).add("false");
				}
			}

		}

//getting possible match for each reciepent... 		
		ArrayList<Integer> reciepentmatch = new ArrayList<>();
		ArrayList<Integer> donoradding = new ArrayList<>();
		for (int j = 0; j < donorsbloodList.size(); j++) {
			donoradding.add(0);
		}
		int matchR = 0;
		for (int i = 0; i < recipientsbloodList.size(); i++) {
			matchR = 0;
			for (int j = 0; j < donorsbloodList.size(); j++) {
				if (posible[i][j])
					matchR++;
			}
			reciepentmatch.add(matchR);
//			System.out.println("Sum of " + (i + 1) + " row: " + matchR);
		}
		// System.out.println(reciepentmatch);
//		System.out.println(reciepentmatch.size());

//print 2D aray to check
//for ( rec = 0; rec < arrayList.size(); rec++) {
//	System.out.println(rec+ " : " + pos.get(rec));
//}
//getting possible match for each donor... 
		int matchD = 0;
		for (int i = 0; i < donorsbloodList.size(); i++) {
			matchD = 0;
			for (int j = 0; j < recipientsbloodList.size(); j++) {
				if (posible[j][i])
					matchD++;

			}
			donoradding.set(i, matchD);
//					System.out.println("Sum of " + (i + 1) + " column: " + matchD);
		}

		pairing(reciepentmatch, donoradding, pos, MatchesR, MatchesD);
	}

	private static void pairing(ArrayList<Integer> arrayList, ArrayList<Integer> arrayListD,
			ArrayList<ArrayList<String>> pos, List<Integer> r, List<Integer> dor) {
		// TODO Auto-generated method stub
		int truth;
		int k;
		// mapping my arraylist so i can sorted out without loosing integer
		Map<Integer, Integer> hashMap = new HashMap<>();
		Map<Integer, Integer> hashMapDonor = new HashMap<>();
		for (k = 0; k < arrayList.size(); k++) {
			hashMap.put(k, arrayList.get(k));
		}

//	    System.out.println(arrayListD);
		for (int i = 0; i < arrayListD.size(); i++) {
			hashMapDonor.put(i, arrayListD.get(i));
			arrayListD.set(i, 0);
		}
//		System.out.println("MAP "+hashMapDonor);
//		System.out.println("MAP 2  "+ sortByValue(hashMapDonor));
		// adding my indexes of hasmap, its going from smallest to bigest in value as
		// added method for sorting
		List<Integer> keys = new ArrayList<Integer>(sortByValue(hashMap).keySet());
		ArrayList<Integer> keysD = new ArrayList<Integer>(sortByValue(hashMapDonor).keySet());
//		System.out.println(" ARRAY " +arrayListD);
//		System.out.println("KEYS " + keysD);
		int index;
		for (int i = 0; i < keys.size(); i++) {
			// adding my indexes from smalles matches to bbiggest if they same order doesnt
			// matter
			index = (int) keys.get(i);
			// System.out.println(index + " -->Integer smallest Reciepent");
			r.add(index);
			truth = findRowWhereColumnEquals(pos.get(index));

			// donors
			if (truth != -1) {
				int nextDonorId = getNextDonor(pos.get(index), keysD, arrayListD);
//				System.out.println("REC " + index + " FOUND DONOR = " + nextDonorId);
				arrayListD.set(nextDonorId, arrayListD.get(nextDonorId) + 1);
//				System.out.println("Donors: " + arrayListD);
				dor.add(nextDonorId);
			}
		}
//  System.out.println("PRINT" +dor);
//		 System.out.println(" and D = " + arrayListD);

	}

	public static HashMap<Integer, Integer> sortByValue(Map<Integer, Integer> hashMap) {
		// Create a list from elements of HashMap
		List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(hashMap.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>();
		for (Map.Entry<Integer, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}

	private static int findRowWhereColumnEquals(ArrayList<String> arrayList) {
		int i = 0;
		for (String row : arrayList) {
			if (row.contains("true")) {

				return i;
			}
			i++;
		}

		return -1;
	}

	private static int getNextDonor(ArrayList<String> arrayList, ArrayList<Integer> keysD, ArrayList<Integer> donnors) {
		int donorsCount = 0;
		int dIndex = 0;
		ArrayList<Integer> posDonnors = new ArrayList<Integer>();

		// 1. FIND DONORS
		for (String row : arrayList) {
			if (row.contains("true")) {
				donorsCount += 1;
				posDonnors.add(dIndex);
			}
			dIndex++;
		}
		// System.out.println("FOUND DONNORS: " + posDonnors);

		// 2. PRIORITISE DONORS
		ArrayList<Integer> prioDonnors = new ArrayList<Integer>();
		for (Integer pDonnor : keysD) {
			if (posDonnors.contains(pDonnor)) {
				prioDonnors.add(pDonnor);
			}
		}
//        System.out.println("PRIO DONNORS: " + prioDonnors);

		int indexToAdd = 0;
		for (int k = 0; k < prioDonnors.size(); k++) {
//			 System.out.println("DONOR " + prioDonnors.get(k) + " , VAL = " +
//			 donnors.get(prioDonnors.get(k)) );

			if (k + 1 < prioDonnors.size()) {
				if (donnors.get(prioDonnors.get(k)) < donnors.get(prioDonnors.get(k + 1))) {
					indexToAdd = k;
					break;
				}
				if (donnors.get(prioDonnors.get(k + 1)) < donnors.get(prioDonnors.get(k))) {
					indexToAdd = k + 1;

					break;
				}
			}

			// posDonorsMap.put(donnors.get(k), donnors.get(donnors.get(k)));
		}
//		 System.out.println("FOUND DONOR " + prioDonnors.get(indexToAdd) + " , VAL = "
//		 + donnors.get(prioDonnors.get(indexToAdd)) );
//		 System.out.println("want to use donor = " + posDonnors.get(indexToAdd));
		return prioDonnors.get(indexToAdd);
	}

	private static List<String> getReciepents(List<String> ListofR, List<String> recipientsbloodList,
			ArrayList<String> recipientsname) {
		// TODO Auto-generated method stub
		BufferedReader reader;
//		List<String> ListofR = new ArrayList<String>();
//		List<String> recipientsList = new ArrayList<String>();
//		ArrayList<String> recipientsname = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader("recipients.txt"));
			String bloodTypeR = reader.readLine();
			int k = 0;
			while (bloodTypeR != null && !bloodTypeR.trim().isEmpty()) {
				String[] splitR = bloodTypeR.split(";");
				if (splitR[1].equalsIgnoreCase("O+") || splitR[1].equalsIgnoreCase("O-")
						|| splitR[1].equalsIgnoreCase("A+") || splitR[1].equalsIgnoreCase("B+")
						|| splitR[1].equalsIgnoreCase("A-") || splitR[1].equalsIgnoreCase("B-")
						|| splitR[1].equalsIgnoreCase("AB+") || splitR[1].equalsIgnoreCase("AB-")) {
					recipientsname.add(splitR[0]);

					recipientsbloodList.add(splitR[1]);
					ListofR.add(splitR[0] + " " + splitR[1]);
					k++;
				} else {
					System.err.println(
							" Error with blood type! At index: " + k + " and name of the person  is :" + splitR[0]);
				}

				bloodTypeR = reader.readLine();
			} // end while

			reader.close();
		} // end try

		catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} // end catch
//		System.out.println(recipientsname);
//		System.out.println(recipientsList);
//		System.out.println("Recipients ----> " + ListofR);
		return ListofR;
	}

	private static List<String> getDonors(List<String> ListofD, List<String> donorsbloodList,
			ArrayList<String> donorname) {
		// TODO Auto-generated method stub
		BufferedReader reader;
//      		ListofD = new ArrayList<String>();
//              donorsList = new ArrayList<String>();
//              donorname = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader("donor.txt"));
			String bloodTypeD = reader.readLine();
			int k = 0;
			while (bloodTypeD != null && !bloodTypeD.trim().isEmpty()) {
				String[] splitD = bloodTypeD.split(";");
				if (splitD[1].equalsIgnoreCase("O+") || splitD[1].equalsIgnoreCase("O-")
						|| splitD[1].equalsIgnoreCase("A+") || splitD[1].equalsIgnoreCase("B+")
						|| splitD[1].equalsIgnoreCase("A-") || splitD[1].equalsIgnoreCase("B-")
						|| splitD[1].equalsIgnoreCase("AB+") || splitD[1].equalsIgnoreCase("AB-")) {
					donorname.add(splitD[0]);

					donorsbloodList.add(splitD[1]);
					ListofD.add(splitD[0] + " " + splitD[1]);
					k++;
				} else {
					System.err.println(
							" Error with blood type! At index " + k + " and name of the person  is : " + splitD[0]);
				}
				bloodTypeD = reader.readLine();
			} // end while

			reader.close();
		} // end try

		catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
		} // end catch

//		System.out.println("Donors --> " + ListofD);
		return ListofD;
	}

	private static boolean bloodtypeMatch(String rec, String don) {
		// TODO Auto-generated method stub
		boolean[][] bloodtype = { { true, false, false, false, false, false, false, false },
			    { true, true, false, false, false, false, false, false },
				{ true, false, true, false, false, false, false, false },
				{ true, true, true, true, false, false, false, false },
				{ true, false, false, false, true, false, false, false },
				{ true, true, false, false, true, true, false, false },
				{ true, false, true, false, true, false, true, false },
				{ true, true, true, true, true, true, true, true },

		};
		String[] bloodInput = { "O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+" };
		int indexR = Arrays.asList(bloodInput).indexOf(rec);
		int indexD = Arrays.asList(bloodInput).indexOf(don);
		if (bloodtype[indexR][indexD]) {
//	    	   System.out.println("Match|| ");
			return true;

		} else {

//	    	   System.out.println("Can't be paired ||");
		}
		return false;
	}

}