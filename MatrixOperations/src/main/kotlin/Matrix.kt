data class Matrix(var rows: Int, var columns: Int) {
    enum class Type {
        Int,
        Double
    }

    private var type = Type.Double
    var elements: MutableList<MutableList<Double>> = mutableListOf()
        set(value) {
            if (value.isEmpty()) throw IllegalStateException("Matrix can't be empty")
            if (value.size != rows || value.first().size != columns)
                throw IllegalStateException("Matrix actual size != input size")
            field = value
            type = determineMatrixType(value)
        }

    companion object {
        private const val lineFirst = " first"
        private const val lineSecond = " second"

        private fun readMatrix(name: String = ""): Matrix {
            try {
                print("Enter size of$name matrix: ")
                val (rows, columns) = readln().split(" ").map { it.toInt() }
                println("Enter$name matrix:")
                val stringMatrix = List(rows) { readln().split(" ") }
                val matrix = Matrix(rows, columns)
                matrix.elements = stringMatrix.map { row ->
                    row.map { it.toDouble() }.toMutableList()
                }.toMutableList()
                return matrix
            } catch (e: NumberFormatException) {
                throw IllegalStateException("Matrix element or matrix sizes isn't Int nor Double")
            }
        }

        fun multiplyMatrices() {
            val matrix1 = readMatrix(lineFirst)
            val matrix2 = readMatrix(lineSecond)

            printMatrix(matrix1.multiply(matrix2))
        }

        fun sumMatrices() {
            val matrix1 = readMatrix(lineFirst)
            val matrix2 = readMatrix(lineSecond)

            printMatrix(matrix1.add(matrix2))
        }

        fun multiplyByConstant() {
            val matrix1 = readMatrix()
            val constant = readConstant()
            if (constant % 1.0 == 0.0)
                printMatrix(matrix1.multiply(constant.toInt()))
            else
                printMatrix(matrix1.multiply(constant))
        }

        private fun readConstant(): Double {
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
                            if (matrix.type == Type.Int) it.toInt().toString() else it.toString()
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
            return rowsFirst == rowsSecond && columnsFirst == columnsSecond
        }

        private fun determineMatrixType(matrix: List<List<Double>>): Type {
            matrix.forEach { row -> row.forEach { if (it % 1.0 != 0.0) return Type.Double } }
            return Type.Int
        }

        fun transposeMatrix() {
            TODO("Not yet implemented")
        }

        fun transposeMatrixSideDiagonal() {
            TODO("Not yet implemented")
        }

        fun transposeMatrixVerticalLine() {
            TODO("Not yet implemented")
        }

        fun transposeMatrixHorizontalLine() {
            TODO("Not yet implemented")
        }
    }

    fun add(other: Matrix): Matrix {
        if (!checkMatrixSizes(
                elements.size,
                elements.first().size,
                other.elements.size,
                other.elements.first().size,
            )
        ) throw IllegalStateException("Matrices have different sizes")

        elements = elements.mapIndexed { rowInd, row ->
            row.mapIndexed { columnInd, it ->
                it + other.elements[rowInd][columnInd]
            }.toMutableList()
        }.toMutableList()
        return this
    }

    fun multiply(constant: Double): Matrix {
        elements = elements.map { row -> row.map { it * constant }.toMutableList() }.toMutableList()
        return this
    }

    fun multiply(constant: Int): Matrix {
        elements = elements.map { row -> row.map { it * constant }.toMutableList() }.toMutableList()
        return this
    }

    fun multiply(other: Matrix): Matrix {
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

}