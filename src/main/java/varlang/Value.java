package varlang;

public interface Value {

    class NumVal implements Value {
        private final double _val;

        public NumVal(double v) {
            _val = v;
        }

        public double v() {
            return _val;
        }

        @Override
        public String toString() {
            int tmp = (int) _val;
            if (tmp == _val) return "" + tmp;
            return "" + _val;
        }
    }

    class UnitVal implements Value {
        public static final UnitVal v = new UnitVal();

        @Override
        public String toString() {
            return "";
        }
    }

    class DynamicError implements Value {
        private final String message;

        public DynamicError(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
