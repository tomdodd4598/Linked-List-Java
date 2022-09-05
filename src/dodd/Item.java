package dodd;

import java.util.Iterator;
import java.util.function.*;

public class Item<T> implements Iterable<Item<T>> {
	
	public final T value;
	public Item<T> next;
	
	public Item(T value, Item<T> next) {
		this.value = value;
		this.next = next;
	}
	
	public Item<T> printGetNext() {
		System.out.print(value);
		System.out.print(next == null ? "\n" : ", ");
		return next;
	}
	
	@Override
	public Iterator<Item<T>> iterator() {
		return new Iterator<Item<T>>() {
			
			Item<T> item = Item.this;
			
			@Override
			public boolean hasNext() {
				return item != null;
			}
			
			@Override
			public Item<T> next() {
				Item<T> next = item;
				item = item.next;
				return next;
			}
		};
	}
	
	public static <T, A, R> R fold(TriFunction<Item<T>, Item<T>, A, A> fSome, BiFunction<Item<T>, A, R> fLast, Function<A, R> fEmpty, A accumulator, Item<T> item) {
		if (item != null) {
			Item<T> next = item.next;
			if (next != null) {
				return fold(fSome, fLast, fEmpty, fSome.apply(item, next, accumulator), next);
			}
			else {
				return fLast.apply(item, accumulator);
			}
		}
		else {
			return fEmpty.apply(accumulator);
		}
	}
	
	public static <T, A, R> R foldback(TriFunction<Item<T>, Item<T>, A, A> fSome, Function<Item<T>, A> fLast, Supplier<A> fEmpty, Function<A, R> generator, Item<T> item) {
		if (item != null) {
			Item<T> next = item.next;
			if (next != null) {
				return foldback(fSome, fLast, fEmpty, innerVal -> generator.apply(fSome.apply(item, next, innerVal)), next);
			}
			else {
				return generator.apply(fLast.apply(item));
			}
		}
		else {
			return generator.apply(fEmpty.get());
		}
	}
}
