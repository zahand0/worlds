object Matrix {
    private const val lineFirst = " first"
    private const val lineSecond = " second"

    fun addMatrices(): Boolean {
        val rawMatrix1 = readMatrix(lineFirst)
        val rawMatrix2 = readMatrix(lineSecond)

        if (!checkMatrixSizes(
                rawMatrix1.size,
                rawMatrix1.first().size,
                rawMatrix2.size,
                rawMatrix2.first().size,
            )
        ) return false

        return when {
            isMatrixInt(rawMatrix1) && isMatrixInt(rawMatrix2) -> {
                val matrix1 = transformToIntMatrix(rawMatrix1)
                val matrix2 = transformToIntMatrix(rawMatrix2)
                printMatrix(sumMatrix(matrix1, matrix2))
            }
            (isMatrixDouble(rawMatrix1) || isMatrixInt(rawMatrix1)) &&
                    (isMatrixDouble(rawMatrix2) || isMatrixInt(rawMatrix2)) -> {
                val matrix1 = transformToDoubleMatrix(rawMatrix1)
                val matrix2 = transformToDoubleMatrix(rawMatrix2)
                printMatrix(sumMatrix(matrix1, matrix2))
            }
            else -> false
        }

    }

    fun multiplyByConst(): Boolean {
        val rawMatrix = readMatrix()
        val rawConstant = readConstant()

        return when {
            isMatrixInt(rawMatrix) && isConstantInt(rawConstant) -> {
                val matrix = transformToIntMatrix(rawMatrix)
                val constant = rawConstant.toInt()
                printMatrix(multiplyMatrixByConstant(matrix, constant))
            }
            (isMatrixDouble(rawMatrix) || isMatrixInt(rawMatrix)) &&
                    (isConstantDouble(rawConstant) || isConstantInt(rawConstant)) -> {
                val matrix = transformToDoubleMatrix(rawMatrix)
                val constant = rawConstant.toDouble()
                printMatrix(multiplyMatrixByConstant(matrix, constant))
            }
            else -> false
        }
    }

    fun multiplyMatrices(): Boolean {
        val rawMatrix1 = readMatrix(lineFirst)
        val rawMatrix2 = readMatrix(lineSecond)

        if (rawMatrix1.first().size != rawMatrix2.size) return false

        return when {
            isMatrixInt(rawMatrix1) && isMatrixInt(rawMatrix2) -> {
                val matrix1 = transformToIntMatrix(rawMatrix1)
                val matrix2 = transformToIntMatrix(rawMatrix2)
                printMatrix(multiplyMatrix(matrix1, matrix2))
            }
            (isMatrixDouble(rawMatrix1) || isMatrixInt(rawMatrix1)) &&
                    (isMatrixDouble(rawMatrix2) || isMatrixInt(rawMatrix2)) -> {
                val matrix1 = transformToDoubleMatrix(rawMatrix1)
                val matrix2 = transformToDoubleMatrix(rawMatrix2)
                printMatrix(multiplyMatrix(matrix1, matrix2))
            }
            else -> false
        }
    }

    private fun readMatrix(name: String = ""): List<List<String>> {
        print("Enter size of$name matrix: ")
        val (rows, _) = readln().split(" ").map { it.toInt() }
        println("Enter$name matrix:")
        return List(rows) { readln().split(" ") }
    }

    private fun readConstant(): String {
        print("Enter constant:")
        println()
        return readln()
    }

    private fun transformToIntMatrix(matrix: List<List<String>>): List<List<Int>> {
        val resultMatrix = mutableListOf<MutableList<Int>>()
        for (row in matrix) {
            resultMatrix.add(mutableListOf())
            for (el in row) {
                resultMatrix[resultMatrix.lastIndex].add(el.toInt())
            }
        }
        return resultMatrix
    }

    private fun transformToDoubleMatrix(matrix: List<List<String>>): List<List<Double>> {
        val resultMatrix = mutableListOf<MutableList<Double>>()
        for (row in matrix) {
            resultMatrix.add(mutableListOf())
            for (el in row) {
                resultMatrix[resultMatrix.lastIndex].add(el.toDouble())
            }
        }
        return resultMatrix
    }

    private fun isMatrixInt(matrix: List<List<String>>): Boolean {
        for (row in matrix) {
            for (el in row) {
                if (el.toIntOrNull() == null) return false
            }
        }
        return true
    }

    private fun isMatrixDouble(matrix: List<List<String>>): Boolean {
        for (row in matrix) {
            for (el in row) {
                if (el.toDoubleOrNull() == null) return false
            }
        }
        return true
    }

    private fun isConstantInt(constant: String): Boolean {
        return constant.toIntOrNull() != null
    }

    private fun isConstantDouble(constant: String): Boolean {
        return constant.toDoubleOrNull() != null
    }

    private fun checkMatrixSizes(
        rowsFirst: Int,
        columnsFirst: Int,
        rowsSecond: Int,
        columnsSecond: Int
    ): Boolean {
        return rowsFirst == rowsSecond && columnsFirst == columnsSecond
    }

    @JvmName("sumMatrixInt")
    private fun sumMatrix(
        firstMatrix: List<List<Int>>,
        secondMatrix: List<List<Int>>
    ): List<List<Int>> {
        val resultMatrix = mutableListOf<MutableList<Int>>()
        for (i in firstMatrix.indices) {
            resultMatrix.add(mutableListOf())
            for (j in firstMatrix.first().indices) {
                resultMatrix[i].add(firstMatrix[i][j] + secondMatrix[i][j])
            }
        }
        return resultMatrix
    }

    private fun sumMatrix(
        firstMatrix: List<List<Double>>,
        secondMatrix: List<List<Double>>
    ): List<List<Double>> {
        val resultMatrix = mutableListOf<MutableList<Double>>()
        for (i in firstMatrix.indices) {
            resultMatrix.add(mutableListOf())
            for (j in firstMatrix.first().indices) {
                resultMatrix[i].add(firstMatrix[i][j] + secondMatrix[i][j])
            }
        }
        return resultMatrix
    }

    private fun <T> printMatrix(matrix: List<List<T>>?): Boolean {
        println( "The result is:\n" +
                (matrix?.joinToString("\n") { row ->
                row.joinToString(" ") { it.toString() }
            } ?: return false)
        )
        return true
    }

    private fun multiplyMatrixByConstant(
        matrix: List<List<Int>>,
        constant: Int
    ): List<List<Int>> {
        return matrix.map { row -> row.map { it * constant } }
    }

    private fun multiplyMatrixByConstant(
        matrix: List<List<Double>>,
        constant: Double
    ): List<List<Double>> {
        return matrix.map { row -> row.map { it * constant } }
    }

    @JvmName("multiplyMatrixInt")
    private fun multiplyMatrix(
        firstMatrix: List<List<Int>>,
        secondMatrix: List<List<Int>>
    ): List<List<Int>> {
        val resultMatrix = mutableListOf<MutableList<Int>>()
        for (row in firstMatrix.indices) {
            resultMatrix.add(mutableListOf())
            for (column in secondMatrix.first().indices) {
                resultMatrix[row].add(0)
                for (i in firstMatrix.first().indices) {
                    resultMatrix[row][column] += firstMatrix[row][i] * secondMatrix[i][column]
                }
            }
        }
        return resultMatrix
    }

    private fun multiplyMatrix(
        firstMatrix: List<List<Double>>,
        secondMatrix: List<List<Double>>
    ): List<List<Double>> {
        val resultMatrix = mutableListOf<MutableList<Double>>()
        for (row in firstMatrix.indices) {
            resultMatrix.add(mutableListOf())
            for (column in secondMatrix.first().indices) {
                resultMatrix[row].add(0.0)
                for (i in firstMatrix.first().indices) {
                    resultMatrix[row][column] += firstMatrix[row][i] * secondMatrix[i][column]
                }
            }
        }
        return resultMatrix
    }

}