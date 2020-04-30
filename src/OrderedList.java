import java.util.*;


class Node<T> {
    public T value;
    public Node<T> next, prev;

    public Node(T _value) {
        value = _value;
        next = null;
        prev = null;
    }
}

public class OrderedList<T> {
    public Node<T> head, tail;
    private boolean _ascending;

    public OrderedList(boolean asc) {
        head = null;
        tail = null;
        _ascending = asc;
    }

    public int compare(T v1, T v2) {
        // <0 если v1 < v2
        // 0 если v1 == v2
        // >0 если v1 > v2

        // если сравниваемые значения - строки
        if (v1 instanceof String) {
            String str1 = ((String) v1).trim();
            String str2 = ((String) v2).trim();
            return str1.compareTo(str2);
        }
        // если сравниваемые значения - числа
        if (v1 instanceof Integer) {
            return Integer.compare((Integer) v1, (Integer) v2);
        }
        return 0;
    }

    // автоматическая вставка value в нужную позицию
    public void add(T value) {
        // создаем новый узел
        Node<T> newNode = new Node<>(value);
        if (head == null) { // если список пустой
            head = newNode;
        } else {
            // проход по всем узлам
            Node<T> node = head;
            while (node != null) {
                if ((compare(node.value, value) > 0) == _ascending) {
                    if (node.prev != null) { // если узел не головной
                        node.prev.next = newNode;
                        newNode.prev = node.prev;
                    } else { // если узел головной
                        head = newNode;
                    }
                    newNode.next = node;
                    node.prev = newNode;
                    return;
                }
                node = node.next;
            }
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
    }

    // поиск узла по значению
    public Node<T> find(T val) {
        Node<T> node = head;
        while (node != null) {
            if (compare(node.value, val) == 0)
                return node;
            // прекращаем поиск если найден заведомо больший или меньший элемент, нежели искомый
            if((compare(node.value, val) > 0) == _ascending) return null;
            if((compare(node.value, val) <= 0) == !_ascending) return null;
            node = node.next;
        }
        return null;
    }

    // удаление узла по значению
    public void delete(T val) {
        Node<T> resNode = find(val);
        if (resNode == null) {
            return;
        }
        if (resNode.prev == null) {
            if (resNode.next == null) {
                head = null;
                tail = null;
                return;
            }
            head = resNode.next;
            head.prev = null;
            return;
        }
        if (resNode.next == null) {
            tail = resNode.prev;
            tail.next = null;
            return;
        }
        Node<T> prev = resNode.prev;
        Node<T> next = resNode.next;
        prev.next = next;
        next.prev = prev;
    }

    // очистка всего списка с возможностью изменения признака упорядоченности
    public void clear(boolean asc) {
        head = null;
        tail = null;
        _ascending = asc;
    }

    // код подсчёта количества элементов в списке
    public int count() {
        int count = 0;
        Node<T> node = head;
        while (node != null) {
            count++;
            node = node.next;
        }
        return count;
    }

    // выдать все элементы упорядоченного списка в виде стандартного списка
    ArrayList<Node<T>> getAll() {
        ArrayList<Node<T>> r = new ArrayList<>();
        Node<T> node = head;
        while (node != null) {
            r.add(node);
            node = node.next;
        }
        return r;
    }
}