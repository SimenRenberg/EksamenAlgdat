package no.oslomet.cs.algdat.Eksamen;


import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
            Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

            Node<T> p = rot, q = null;               // p starter i roten
            int cmp = 0;                             // hjelpevariabel

            while (p != null)                        // fortsetter til p er ute av treet
            {
                q = p;                                 // q er forelder til p
                cmp = comp.compare(verdi,p.verdi);     // bruker komparatoren
                p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
            }

            // p er nå null, dvs. ute av treet, q er den siste vi passerte

            p = new Node<>(verdi, q);                // oppretter en ny node

            if (q == null) rot = p;                  // p blir rotnode
            else if (cmp < 0) q.venstre = p;         // venstre barn til q
            else q.høyre = p;                        // høyre barn til q

            antall++;                                // én verdi mer i treet
            return true;                             // vellykket innlegging
    }

    //Denne koden er kopiert fra kompendiet - programkode 5.2.8 d)
    //den er blitt endret slik at etter sletting så får barnet (b) riktig forelder, så sant barnet faktisk er opprettet.
    public boolean fjern(T verdi) {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = rot.forelder;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
            if (b!=null) b.forelder = q;
        }
        else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;   // det er nå én node mindre i treet
        return true;
    }

    //Legger treet inn i et ArrayDeque og benytter seg av fjern(T verdi)-metoden hver gang verdien blir tatt ut av køen.
    public int fjernAlle(T verdi) {
        if (rot == null) return 0;
        int antallFjernet = 0;
        ArrayDeque<Node<T>> deque = new ArrayDeque<>();
        deque.add(rot);

        while (!deque.isEmpty()){
            Node<T> curr = deque.removeFirst();
            if (curr.verdi == verdi){
                if(fjern(verdi)){
                    antallFjernet++;
                }
            }
            if (curr.venstre != null) deque.addFirst(curr.venstre);
            if (curr.høyre != null) deque.addFirst(curr.høyre);
        }

        return antallFjernet;
    }

    //Går inn i treet med dybde-først ved hjelp av et arraydeque, og teller hver gang
    //verdien blir funnet når en node blir tatt ut av køen.
    public int antall(T verdi) {
        int antall = 0;

        ArrayDeque<Node<T>> deque = new ArrayDeque<>();
        deque.add(rot);

        while (!deque.isEmpty()){
            Node<T> current = deque.removeFirst();

            //verdien er funnet
            if (current.verdi == verdi){
                antall++;
            }

            //hvis ikke, fortsett nedover.
            if (current.venstre != null){
                deque.addFirst(current.venstre);
            }

            if (current.høyre != null){
                deque.addFirst(current.høyre);
            }

        }
        return antall;
    }


    public void nullstill() {

        //Så lenge roten finnes
        if (rot != null) {
            //og ikke bare roten finnes
            if (antall != 1){
                //nuller alt nedover hele treet
                ArrayDeque<Node<T>> deque = new ArrayDeque<>();
                deque.addFirst(rot);
                while (!deque.isEmpty()) {
                    Node<T> current = deque.removeFirst();

                    if (current.venstre != null) {
                        deque.addFirst(current.venstre);
                    }

                    if (current.høyre != null) {
                        deque.addFirst(current.høyre);
                    }
                    current.høyre = null;
                    current.venstre = null;
                    current.forelder = null;
                    antall--;
                    endringer++;
                }
            } else {
                //Hvis det kun er treet som finnes, null det.
                rot = null;
                antall--;
                endringer++;
            }
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        //dersom vi kan gå til venstre
        if (p.venstre != null){
            return førstePostorden(p.venstre);

            //dersom vi ikke kan gå til venstre lenger.
            //rekursjonen vil fortsatt fortsette til venstre om p.høyre har et venstrebarn.
        } else if (p.høyre != null){
            return førstePostorden(p.høyre);

            //vi er lengst ned til venstre i treet.
        } else {
            return p;
        }

    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null){
            return null;
        }
        //hvis p er sin foreldres høyre, så er forelderen den neste
        if (p.forelder.høyre == p){
            return p.forelder;

        // Hvis p ikke har en til høyre, så er p sin foreldre den neste
        } else if (p.forelder.høyre == null){
            return p.forelder;

        //hvis ikke, så vil nestemann være det som førstePostorder() returnerer. (fortsetter så langt til venstre som er mulig).
        //tilfellet her er: p er sin foreldres venstre, og foreldren har en høyrenode.
        } else {
                return førstePostorden(p.forelder.høyre);
        }

    }

    public void postorden(Oppgave<? super T> oppgave) {
        //begynn ved første i postorden
        Node<T> p = førstePostorden(rot);

        //mens det finnes verdier, fortsett i postorden.
        while (p != null){
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        //så lenge p ikke er null,
        if (p == null){
            return;
        }
        //traverser treet i postorden og utfør oppgaven
        postordenRecursive(p.venstre, oppgave);
        postordenRecursive(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    //samme metodikk som i fjernAlle(), og Antall().
    //legger en og en nodes verdi i et array, med FIFO.
    public ArrayList<T> serialize() {
        ArrayDeque<Node<T>> deque = new ArrayDeque<>();
        ArrayList<T> serializedList = new ArrayList<>();
        deque.addFirst(rot);
        while (!deque.isEmpty()) {
            Node<T> current = deque.removeFirst();

            serializedList.add(current.verdi);
            if (current.venstre != null) {
                deque.addLast(current.venstre);
            }

            if (current.høyre != null) {
                deque.addLast(current.høyre);
            }
        }

        return serializedList;
    }

    //fordi data-listen har blitt populert med FIFO, vil en enkel loop fylle treet riktig.
    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> deserializedTree = new EksamenSBinTre<>(c);
        for (var item : data) {
            deserializedTree.leggInn(item);
        }
        return deserializedTree;
    }


} // ObligSBinTre
