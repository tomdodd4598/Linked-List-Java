package dodd;

import java.util.function.*;

public class Helpers {
	
	public static <T> Item<T> insertItem(Item<T> start, T val, BiFunction<T, Item<T>, Boolean> insertBefore) {
		System.out.println(String.format("Creating item: %s", val));
		Item<T> current = start, previous = null;
		
		while (current != null && !insertBefore.apply(val, current)) {
			previous = current;
			current = current.next;
		}
		Item<T> item = new Item<T>(val, current);
		
		if (previous == null) {
			start = item;
		}
		else {
			previous.next = item;
		}
		
		return start;
	}
	
	public static <T> Item<T> removeItem(Item<T> start, T val, BiFunction<Item<T>, T, Boolean> valueEquals) {
		Item<T> current = start, previous = null;
		
		while (current != null && !valueEquals.apply(current, val)) {
			previous = current;
			current = current.next;
		}
		
		if (current == null) {
			System.out.println(String.format("Item %s does not exist!", val));
		}
		else {
			if (previous == null) {
				start = current.next;
			}
			else {
				previous.next = current.next;
			}
			System.out.println(String.format("Removed item: %s", val));
		}
		
		return start;
	}
	
	public static <T> Item<T> removeAll(Item<T> start) {
		return null;
	}
	
	public static <T> void printLoop(Item<T> start) {
		while (start != null) {
			start = start.printGetNext();
		}
	}
	
	public static <T> void printIterator(Item<T> start) {
		if (start != null) {
			for (Item<T> item : start) {
				item.printGetNext();
			}
		}
	}
	
	public static <T> void printRecursive(Item<T> start) {
		if (start != null) {
			printRecursive(start.printGetNext());
		}
	}
	
	public static <T> void printFold(Item<T> start) {
		TriFunction<Item<T>, Item<T>, String, String> fSome = (current, next, accumulator) -> String.format("%s%s, ", accumulator, current.value);
		BiFunction<Item<T>, String, String> fLast = (current, accumulator) -> String.format("%s%s\n", accumulator, current.value);
		Function<String, String> fEmpty = accumulator -> accumulator;
		System.out.print(Item.fold(fSome, fLast, fEmpty, "", start));
	}
	
	public static <T> void printFoldback(Item<T> start) {
		TriFunction<Item<T>, Item<T>, String, String> fSome = (current, next, innerVal) -> String.format("%s, %s", current.value, innerVal);
		Function<Item<T>, String> fLast = current -> String.format("%s\n", current.value);
		Supplier<String> fEmpty = () -> "";
		System.out.print(Item.foldback(fSome, fLast, fEmpty, Function.identity(), start));
	}
}
