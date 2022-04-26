data class Matrix(var rows: Int, var columns: Int) {
    enum class Type {
        /**
         * Matrix type
         */
        Int,
        Double
    }

    private var type = Type.Double // default value is Double

    // elements of matrix, stored in Double
    var elements: MutableList<MutableList<Double>> = mutableListOf()
        set(value) {
            if (value.isEmpty()) throw IllegalStateException("Matrix can't be empty")
            if (value.size != rows || value.first().size != columns)
                throw IllegalStateException("Matrix actual size != input size")
            field = value
            // setting type of matrix
            type = determineMatrixType(value)
        }

    companion object {
        private const val lineFirst = " first"
        private const val lineSecond = " second"

        private fun readMatrix(name: String = ""): Matrix {
            /**
             * Reading matrix sizes and 2-dimensional list<Double>
             *
             * return: actual Matrix instance
             */
            try {
                print("Enter size of$name matrix: ")
                val (rows, columns) = readln().split(" ").map { it.toInt() }
                println("Enter$name matrix:")
                val stringMatrix = List(rows) { readln().split(" ") }
                val matrix = Matrix(rows, columns)
                // transform list of String to Double
                matrix.elements = stringMatrix.map { row ->
                    row.map { it.toDouble() }.toMutableList()
                }.toMutableList()
                return matrix
            } catch (e: NumberFormatException) {
                throw IllegalStateException("Matrix element or matrix sizes isn't Int nor Double")
            }
        }

        fun multiplyMatrices() {
            /**
             * Reading 2 matrices, print result of multiplying them
             */
            val matrix1 = readMatrix(lineFirst)
            val matrix2 = readMatrix(lineSecond)

            printMatrix(matrix1.multiply(matrix2))
        }

        fun sumMatrices() {
            /**
             * Reading 2 matrices, print result of summing them
             */
            val matrix1 = readMatrix(lineFirst)
            val matrix2 = readMatrix(lineSecond)

            printMatrix(matrix1.add(matrix2))
        }

        fun multiplyByConstant() {
            /**
             * Reading matrix and constant, print result of multiplying them
             */
            val matrix1 = readMatrix()
            val constant = readConstant() // read as Double
            // if constant is actually int type
            if (constant % 1.0 == 0.0)
                printMatrix(matrix1.multiply(constant.toInt()))
            else
                printMatrix(matrix1.multiply(constant))
        }

        private fun readConstant(): Double {
            /**
             * Reading constant
             *
             * return: Double type number
             */
            print("Enter constant:")
            println()
            return readln().toDouble()
        }

        private fun printMatrix(matrix: Matrix) {
            /**
             * Printing all matrix elements,
             * if matrix type is int: transform all elements to Int previously
             */
            println("The result is:\n" +
                    matrix.elements.joinToString("\n") { row ->
                        row.joinToString(" ") {
                            if (matrix.type == Type.Int) it.toInt().toString() else
                                String.format("%.2f", it)
                        }
                    }
            )
        }

        private fun checkMatrixSizes(
            rowsFirst: Int,
            columnsFirst: Int,
            rowsSecond: Int,
            columnsSecond: Int
        ): Boolean {
            /**
             * Checking if num of first matrix rows equal to num of second matrix rows and
             * num of first matrix columns equal to num of second matrix columns
             */
            return rowsFirst == rowsSecond && columnsFirst == columnsSecond
        }

        private fun determineMatrixType(matrix: List<List<Double>>): Type {
            /**
             * Check if all matrix elements is Int or Double
             *
             * return: Type.Int or Type.Double
             */
            matrix.forEach { row -> row.forEach { if (it % 1.0 != 0.0) return Type.Double } }
            return Type.Int
        }

        fun transposeMatrix() {
            /**
             * Reading matrix, printing result of transposing it
             */
            val matrix1 = readMatrix()
            printMatrix(matrix1.transpose())
        }

        fun transposeMatrixSideDiagonal() {
            /**
             * Reading matrix, printing result of side diagonal transposing it
             */
            val matrix1 = readMatrix()
            printMatrix(matrix1.transposeSideDiagonal())
        }

        fun transposeMatrixVerticalLine() {
            /**
             * Reading matrix, printing result of vertically transposing it
             */
            val matrix1 = readMatrix()
            printMatrix(matrix1.transposeVertical())
        }

        fun transposeMatrixHorizontalLine() {
            /**
             * Reading matrix, printing result of horizontally transposing it
             */
            val matrix1 = readMatrix()
            printMatrix(matrix1.transposeHorizontal())
        }

        fun matrixDeterminant() {
            /**
             * Reading matrix, printing its determinant
             */
            val matrix = readMatrix()
            println("The result is:")
            // if matrix type is int print int value
            println(if (matrix.type == Type.Int) matrix.determinant().toInt() else matrix.determinant())
        }

        fun inverseMatrix() {
            /**
             * Reading matrix, printing its inverse
             */
            val matrix = readMatrix()
            if (matrix.determinant() == 0.0) {
                println("This matrix doesn't have an inverse.")
            } else {
                printMatrix(matrix.inversed())
            }
        }
    }

    private fun inversed(): Matrix {
        /**
         * Calculating inversed matrix
         */
        return getCofactorMatrix().transpose().multiply(1.0 / determinant())
    }

    private fun getCofactorMatrix(): Matrix {
        /**
         * Calculating cofactor matrix
         */
        // creating new elements list
        val cofactorMatrixElements: MutableList<MutableList<Double>> = mutableListOf()
        for (row in 0 until rows) {
            cofactorMatrixElements.add(mutableListOf())
            for (column in 0 until columns) {
                val sign = if (column + row and 1 == 0) 1 else -1
                cofactorMatrixElements[cofactorMatrixElements.lastIndex].add(
                    sign * getMinor(row, column).determinant()
                )
            }
        }
        val cofactorMatrix = Matrix(rows, columns)
        cofactorMatrix.elements = cofactorMatrixElements
        return cofactorMatrix

    }

    private fun determinant(): Double {
        /**
         * Calculating matrix determinant
         */
        // matrix must be square
        if (rows != columns) throw IllegalStateException("Matrix must square")
        // if matrix size is 1x1 result its element
        if (rows == 1) return elements[0][0]
        var determinant = 0.0
        for (column in 0 until columns) {
            // if indexes sum is divided by 2, then minor adds with + sign (row index always equal 0)
            val sign = if (column and 1 == 0) 1 else -1
            // recursive call to calculate minor determinant
            determinant += elements[0][column] * getMinor(0, column).determinant() * sign
        }
        return determinant
    }

    fun getMinor(row: Int, col: Int): Matrix {
        /**
         * Return minor of matrix without 'row' row and 'col' column
         */
        // creating new elements list
        val newMatrixElements: MutableList<MutableList<Double>> = mutableListOf()
        for (rowInd in 0 until rows) {
            // skip 'row' row
            if (rowInd == row) continue
            newMatrixElements.add(mutableListOf())
            for (columnInd in 0 until columns) {
                // skip 'col' column
                if (columnInd == col) continue
                newMatrixElements[newMatrixElements.lastIndex].add(elements[rowInd][columnInd])
            }
        }
        // creating new matrix
        val newMatrix = Matrix(rows - 1, columns - 1)
        newMatrix.elements = newMatrixElements
        return newMatrix
    }

    fun add(other: Matrix): Matrix {
        /**
         * Adding another matrix
         * Changes the initial matrix
         *
         * return: sum of two matrices
         */
        // check if matrices have different sizes
        if (!checkMatrixSizes(
                elements.size,
                elements.first().size,
                other.elements.size,
                other.elements.first().size,
            )
        ) throw IllegalStateException("Matrices have different sizes")
        // adding matrices element by element
        elements = elements.mapIndexed { rowInd, row ->
            row.mapIndexed { columnInd, it ->
                it + other.elements[rowInd][columnInd]
            }.toMutableList()
        }.toMutableList()
        return this
    }

    fun multiply(constant: Double): Matrix {
        /**
         * Multiply all matrix element by Double constant
         * Changes the initial matrix
         */
        elements = elements.map { row -> row.map { it * constant }.toMutableList() }.toMutableList()
        return this
    }

    fun multiply(constant: Int): Matrix {
        /**
         * Multiply all matrix element by Int constant
         * Changes the initial matrix
         */
        elements = elements.map { row -> row.map { it * constant }.toMutableList() }.toMutableList()
        return this
    }

    fun multiply(other: Matrix): Matrix {
        /**
         * Multiply by another matrix
         * Changes the initial matrix
         */
        // first matrix columns number must be equal to second matrix rows number
        if (elements.first().size != other.elements.size)
            throw IllegalStateException("Matrices have incompatible sizes")
        val resultElements = mutableListOf<MutableList<Double>>()
        for (row in 0 until rows) {
            resultElements.add(mutableListOf())
            for (column in 0 until other.columns) {
                resultElements[row].add(0.0)
                for (i in 0 until columns) {
                    resultElements[row][column] += elements[row][i] * other.elements[i][column]
                }
            }
        }
        columns = other.columns
        elements = resultElements
        return this
    }

    fun transpose(): Matrix {
        /**
         * Transpose matrix
         * Changes the initial matrix
         */
        val newElements = MutableList(columns) { MutableList(rows) { 0.0 } }
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                newElements[column][row] = elements[row][column]
            }
        }
        rows = columns.also { columns = rows }
        elements = newElements
        return this
    }

    fun transposeSideDiagonal(): Matrix {
        /**
         * Transpose matrix along the side diagonal
         * Changes the initial matrix
         */
        val newElements = MutableList(columns) { MutableList(rows) { 0.0 } }
        for (row in 0 until rows) {
            for (column in 0 until columns) {
                newElements[columns - column - 1][rows - row - 1] = elements[row][column]
            }
        }
        rows = columns.also { columns = rows }
        elements = newElements
        return this
    }

    fun transposeVertical(): Matrix {
        /**
         * Transpose matrix along the column
         * Changes the initial matrix
         */
        for (row in 0 until rows) {
            for (column in 0 until columns / 2) {
                elements[row][column] = elements[row][columns - column - 1].also {
                    elements[row][columns - column - 1] = elements[row][column]
                }
            }
        }
        return this
    }

    fun transposeHorizontal(): Matrix {
        /**
         * Transpose matrix along the row
         * Changes the initial matrix
         */
        for (row in 0 until rows / 2) {
            for (column in 0 until columns) {
                elements[row][column] = elements[rows - row - 1][column].also {
                    elements[rows - row - 1][column] = elements[row][column]
                }
            }
        }
        return this
    }

}