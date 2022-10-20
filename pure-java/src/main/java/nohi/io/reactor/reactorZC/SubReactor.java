package nohi.io.reactor.reactorZC;

import lombok.extern.slf4j.Slf4j;
import nohi.io.reactor.zctest.DispatchSelectKey;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @author NOHI
 * 2021-08-17 23:05
 **/
@Slf4j
public class SubReactor extends DispatchSelectKey implements Runnable {
    private Selector selector;
    private int index;

    public SubReactor(Selector selector, int i) {
        this.selector = selector;
        this.index = i;
    }

    @Override
    public void run() {
        log.debug("SubReactor.run()..." + index);
        while (true) {
            try {
                selector.select();
                System.out.println(index + " selector:" + selector.toString() + "thread:" + Thread.currentThread().getName());
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    log.debug("KEY:{}", this.getKeyType(selectionKey));
                    if (!selectionKey.isValid()) {
                        log.warn("key isValid =  ", selectionKey.isValid());
                        selector.close();
                    }
                    this.dispatcher(selectionKey);
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
