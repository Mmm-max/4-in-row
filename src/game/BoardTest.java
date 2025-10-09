package game;

import GUI.ConnectFourGUI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class BoardTest {

    @Test
    @Tag("isWinning")
    @DisplayName("Проверка, что горизонтальная победа работает корректно")
    public void testHorizontalWin() {
        System.out.println("testHorizontalWin");
        Board board = new Board();
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        board.makeMove(3, 1);
//        board.printBoard();
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("isWinning")
    @DisplayName("Проверка, что вертикальная победа работает корректно")
    public void testVerticalWin() {
        System.out.println("testVerticalWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        assertEquals(1, board.isWinningMove(0, 1));
    }

    @Test
    @Tag("isWinning")
    @DisplayName("Проверка, что диагональная победа работает корректно")
    public void testDiagonalLeftWin() {
        System.out.println("testDiagonalLeftWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 2);
        board.makeMove(0, 2);
        board.makeMove(0, 2);
        board.makeMove(0, 1);

        board.makeMove(1, 2);
        board.makeMove(1, 2);
        board.makeMove(1, 1);

        board.makeMove(2, 2);
        board.makeMove(2, 1);

        board.makeMove(3, 1);
//        board.printBoard();
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("isWinning")
    @DisplayName("Проверка, что диагональная победа работает корректно")
    public void testDiagonalRightWin() {
        System.out.println("testDiagonalRightWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 2);
        board.makeMove(1, 1);
        board.makeMove(2, 2);
        board.makeMove(2, 2);
        board.makeMove(2, 1);
        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 1);
//        board.printBoard();
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("isWinning")
    @DisplayName("Проверка, что диагональная победа работает корректно")
    public void testDiagonalLeftWin2() {
        System.out.println("testDiagonalLeftWin2");
        Board board = new Board(7, 6);
        board.makeMove(5, 1);
        board.makeMove(4, 2);

        board.makeMove(4, 1);
        board.makeMove(3, 2);

        board.makeMove(3, 1);
        board.makeMove(2, 2);

        board.makeMove(3, 1);
        board.makeMove(2, 2);

        board.makeMove(2, 1);
        board.makeMove(0, 2);

        board.makeMove(2, 1);
//        board.printBoard();
        assertEquals(1, board.isWinningMove(2, 1));
    }

    @Test
    @Tag("isWinning")
    @DisplayName("Проверка, что отсутствие победы работает корректно")
    public void testNoWin() {
        System.out.println("testNoWin");
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        assertEquals(0, board.isWinningMove(2, 1));
    }

    @Test
    @Tag("isFull")
    @DisplayName("Проверка на отсутсвие ничьи")
    public void testDraw1() {
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        board.makeMove(2, 1);
        board.makeMove(2, 1);
        assertFalse(board.isFull());
    }

    @Test
    @Tag("isFull")
    @DisplayName("Проверка на ничью, когда она есть по подсчету ходов")
    public void testDraw2() {
        Board board = new Board(7, 6);
        board.setMoves(7 * 6);
        assertTrue(board.isFull());
    }

    @Test
    @Tag("makeMove")
    @DisplayName("Проверка на корректность добавления ходов на доску")
    public void testMove1() {
        Board board = new Board(7, 6);
        board.makeMove(1, 1);
        assertEquals(0, board.makeMove(1, 2));
//        board.printBoard();
        assertEquals(1, board.getCell(1, 5));
        assertEquals(2, board.getCell(1, 4));
        assertEquals(0, board.getCell(0, 2));
        assertEquals(2, board.getMoves());
    }

    @Test
    @Tag("makeMove")
    @DisplayName("Проверка на корректность добавления ходов на доску")
    public void testMove2() {
        Board board = new Board(7, 6);
        assertEquals(-1, board.makeMove(10, 5));
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        assertEquals(1, board.makeMove(3, 1));
    }

    @Test
    @Tag("IsLegalMove")
    @DisplayName("Проверка на легитивность ходов")
    public void testIsLegalMove1() {
        Board board = new Board(7, 6);
        assertFalse(board.isLegalMove(7));
        assertTrue(board.isLegalMove(0));
        assertFalse(board.isLegalMove(-1));
    }

    @Test
    @Tag("IsLegalMove")
    @DisplayName("Проверка на легитивность ходов")
    public void testIsLegalMove2() {
        Board board = new Board(7, 6);
        board.makeMove(1, 1);
        board.makeMove(1, 2);
        board.makeMove(1, 1);
        board.makeMove(1, 1);
        board.makeMove(1, 2);
        board.makeMove(1, 1);
        assertFalse(board.isLegalMove(1));
        assertTrue(board.isLegalMove(2));
    }

    @Test
    @Tag("GetCurrPlayer")
    @DisplayName("Правильность отображения игрока")
    public void testGetCurrPlayer1() {
        Board board = new Board(7, 6);
        assertEquals(1, board.getCurrPlayer());
        board.switchPlayer();
        assertEquals(2, board.getCurrPlayer());
    }

    @Test
    @Tag("checkLine")
    @DisplayName("Тест граничных условий для checkLine - выход за левую границу")
    public void testCheckLineBoundaryLeft() {
        Board board = new Board(7, 6);
        // Размещаем фишки у левого края
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        // Проверяем что метод корректно обрабатывает выход за границу currentX < 0
        assertEquals(1, board.isWinningMove(0, 1));
    }

    @Test
    @Tag("checkLine")
    @DisplayName("Тест граничных условий для checkLine - выход за правую границу")
    public void testCheckLineBoundaryRight() {
        Board board = new Board(7, 6);
        // Размещаем фишки у правого края
        int rightCol = 6; // последний столбец
        board.makeMove(rightCol, 1);
        board.makeMove(rightCol, 1);
        board.makeMove(rightCol, 1);
        board.makeMove(rightCol, 1);
        assertEquals(1, board.isWinningMove(rightCol, 1));
    }

    @Test
    @Tag("checkLine")
    @DisplayName("Тест граничных условий для checkLine - выход за верхнюю границу")
    public void testCheckLineBoundaryTop() {
        Board board = new Board(7, 6);
        // Заполняем столбец почти полностью для проверки currentY < 0
        board.makeMove(3, 1);
        board.makeMove(3, 2);
        board.makeMove(3, 1);
        board.makeMove(3, 2);
        board.makeMove(3, 1);
        board.makeMove(3, 2);
        // Последний ход в верхнюю позицию
        assertEquals(-1, board.makeMove(3, 1));
    }

    @Test
    @Tag("checkLine")
    @DisplayName("Тест диагональной победы у границы - правый верхний угол")
    public void testDiagonalWinAtBoundary() {
        Board board = new Board(7, 6);
        // Создаем диагональ, которая упирается в границы
        board.makeMove(6, 2); // правый край
        board.makeMove(6, 2);
        board.makeMove(6, 2);
        board.makeMove(6, 1);

        board.makeMove(5, 2);
        board.makeMove(5, 2);
        board.makeMove(5, 1);

        board.makeMove(4, 2);
        board.makeMove(4, 1);

        board.makeMove(3, 1);
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("checkLine")
    @DisplayName("Тест вычисления координат currentX и currentY с отрицательными смещениями")
    public void testNegativeOffsetCalculation() {
        Board board = new Board(7, 6);
        // Тест для проверки корректности вычисления currentX = x + i * dx при отрицательных i и dx
        board.makeMove(3, 1);
        board.makeMove(2, 2);
        board.makeMove(2, 1);
        board.makeMove(1, 2);
        board.makeMove(1, 2);
        board.makeMove(1, 1);
        board.makeMove(0, 2);
        board.makeMove(0, 2);
        board.makeMove(0, 2);
        board.makeMove(0, 1);
        // Проверяем левую диагональ при i = -3, dx = -1
        assertEquals(1, board.isWinningMove(0, 1));
    }

    @Test
    @Tag("checkLine")
    @DisplayName("Тест максимальных координат currentX >= board[0].length")
    public void testMaxBoundaryX() {
        Board board = new Board(4, 4); // Маленькая доска для легкого достижения границ
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        board.makeMove(3, 1);
        // При проверке горизонтали с i=3, dx=1 получим currentX = 3 + 3*1 = 6 >= 4
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("checkLine")
    @DisplayName("Тест максимальных координат currentY >= board.length")
    public void testMaxBoundaryY() {
        Board board = new Board(7, 4); // Высота = 4
        // Заполняем колонку полностью
        board.makeMove(3, 1);
        board.makeMove(3, 1);
        board.makeMove(3, 1);
        board.makeMove(3, 1);
        // При вертикальной проверке currentY может выйти за границы
        assertEquals(1, board.isWinningMove(3, 1));
    }

    @Test
    @Tag("get_y_coord")
    @DisplayName("Тест поиска свободной позиции в пустом столбце")
    public void testGetYCoordEmptyColumn() {
        Board board = new Board(7, 6);
        // В пустом столбце должна возвращаться нижняя позиция (height-1)
        int result = board.makeMove(3, 1);
        assertEquals(0, result); // не победа
        assertEquals(1, board.getCell(3, 5)); // фишка должна быть в нижней позиции
    }

    @Test
    @Tag("get_y_coord")
    @DisplayName("Тест поиска свободной позиции в частично заполненном столбце")
    public void testGetYCoordPartiallyFilled() {
        Board board = new Board(7, 6);
        // Заполняем столбец частично
        board.makeMove(2, 1); // позиция y=5
        board.makeMove(2, 2); // позиция y=4
        board.makeMove(2, 1); // позиция y=3

        // Следующая фишка должна попасть на позицию y=2
        board.makeMove(2, 2);
        assertEquals(2, board.getCell(2, 2)); // проверяем что фишка на правильной позиции
    }

    @Test
    @Tag("get_y_coord")
    @DisplayName("Тест поиска позиции в полностью заполненном столбце")
    public void testGetYCoordFullColumn() {
        Board board = new Board(7, 6);
        // Заполняем столбец полностью
        board.makeMove(1, 1); // y=5
        board.makeMove(1, 2); // y=4
        board.makeMove(1, 1); // y=3
        board.makeMove(1, 2); // y=2
        board.makeMove(1, 1); // y=1
        board.makeMove(1, 2); // y=0

        // Попытка добавить еще одну фишку должна вернуть -1
        assertEquals(-1, board.makeMove(1, 1));
    }

    @Test
    @Tag("get_y_coord")
    @DisplayName("Тест корректности заполнения снизу вверх")
    public void testGetYCoordFillingOrder() {
        Board board = new Board(7, 6);

        // Добавляем фишки по одной и проверяем правильность позиций
        board.makeMove(4, 1);
        assertEquals(1, board.getCell(4, 5)); // первая фишка внизу
        assertEquals(0, board.getCell(4, 4)); // выше пусто

        board.makeMove(4, 2);
        assertEquals(2, board.getCell(4, 4)); // вторая фишка выше первой
        assertEquals(0, board.getCell(4, 3)); // еще выше пусто

        board.makeMove(4, 1);
        assertEquals(1, board.getCell(4, 3)); // третья фишка продолжает заполнение вверх
    }

    @Test
    @Tag("get_y_coord")
    @DisplayName("Тест поведения при попытке хода в полный столбец верхней позиции")
    public void testGetYCoordTopPosition() {
        Board board = new Board(7, 6);

        // Заполняем столбец до самого верха
        for (int i = 0; i < 6; i++) {
            board.makeMove(0, (i % 2) + 1);
        }

        // Проверяем что верхняя позиция заполнена
        assertNotEquals(0, board.getCell(0, 0));

        // Попытка добавить еще одну фишку должна быть отклонена
        assertEquals(-1, board.makeMove(0, 1));
    }

    @Test
    @Tag("clone")
    @DisplayName("Тест клонирования пустой доски")
    public void testCloneEmptyBoard() {
        Board originalBoard = new Board(7, 6);
        Board clonedBoard = originalBoard.clone();

        // Проверяем что клон не тот же объект
        assertNotSame(originalBoard, clonedBoard);

        // Проверяем что размеры совпадают
        assertEquals(originalBoard.getWidth(), clonedBoard.getWidth());
        assertEquals(originalBoard.getHeight(), clonedBoard.getHeight());

        // Проверяем что все ячейки пустые и одинаковые
        for (int x = 0; x < originalBoard.getWidth(); x++) {
            for (int y = 0; y < originalBoard.getHeight(); y++) {
                assertEquals(0, clonedBoard.getCell(x, y));
                assertEquals(originalBoard.getCell(x, y), clonedBoard.getCell(x, y));
            }
        }
    }

    @Test
    @Tag("clone")
    @DisplayName("Тест клонирования доски с фишками")
    public void testCloneBoardWithPieces() {
        Board originalBoard = new Board(7, 6);

        // Добавляем фишки
        originalBoard.makeMove(0, 1);
        originalBoard.makeMove(1, 2);
        originalBoard.makeMove(2, 1);
        originalBoard.makeMove(0, 2);

        Board clonedBoard = originalBoard.clone();

        // Проверяем что клон не тот же объект
        assertNotSame(originalBoard, clonedBoard);

        // Проверяем что все фишки скопированы корректно
        for (int x = 0; x < originalBoard.getWidth(); x++) {
            for (int y = 0; y < originalBoard.getHeight(); y++) {
                assertEquals(originalBoard.getCell(x, y), clonedBoard.getCell(x, y));
            }
        }
    }

    @Test
    @Tag("clone")
    @DisplayName("Тест независимости клонированной доски")
    public void testCloneIndependence() {
        Board originalBoard = new Board(7, 6);
        originalBoard.makeMove(3, 1);

        Board clonedBoard = originalBoard.clone();

        // Изменяем оригинальную доску
        originalBoard.makeMove(4, 2);

        // Проверяем что клон не изменился
        assertEquals(1, originalBoard.getCell(3, 5));
        assertEquals(2, originalBoard.getCell(4, 5));
        assertEquals(1, clonedBoard.getCell(3, 5));
        assertEquals(0, clonedBoard.getCell(4, 5)); // в клоне этой фишки быть не должно
    }

    @Test
    @Tag("clone")
    @DisplayName("Тест клонирования досок разных размеров")
    public void testCloneDifferentSizes() {
        Board smallBoard = new Board(3, 3);
        smallBoard.makeMove(1, 1);
        smallBoard.makeMove(2, 2);

        Board clonedSmall = smallBoard.clone();

        assertEquals(smallBoard.getWidth(), clonedSmall.getWidth());
        assertEquals(smallBoard.getHeight(), clonedSmall.getHeight());
        assertEquals(1, clonedSmall.getCell(1, 2));
        assertEquals(2, clonedSmall.getCell(2, 2));

        Board bigBoard = new Board(10, 8);
        bigBoard.makeMove(5, 1);
        bigBoard.makeMove(9, 2);

        Board clonedBig = bigBoard.clone();

        assertEquals(bigBoard.getWidth(), clonedBig.getWidth());
        assertEquals(bigBoard.getHeight(), clonedBig.getHeight());
        assertEquals(1, clonedBig.getCell(5, 7));
        assertEquals(2, clonedBig.getCell(9, 7));
    }

    @Test
    @Tag("getGrid")
    @DisplayName("Тест получения сетки пустой доски")
    public void testGetGridEmpty() {
        Board board = new Board(7, 6);
        int[][] grid = board.getGrid();

        // Проверяем размеры
        assertEquals(6, grid.length); // высота (width в Board)
        assertEquals(7, grid[0].length); // ширина (height в Board)

        // Проверяем что все ячейки пустые
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                assertEquals(0, grid[y][x]);
            }
        }
    }

    @Test
    @Tag("getGrid")
    @DisplayName("Тест получения сетки с фишками")
    public void testGetGridWithPieces() {
        Board board = new Board(7, 6);

        // Добавляем фишки
        board.makeMove(0, 1); // должна быть в grid[5][0]
        board.makeMove(0, 2); // должна быть в grid[4][0]
        board.makeMove(3, 1); // должна быть в grid[5][3]
        board.makeMove(6, 2); // должна быть в grid[5][6]

        int[][] grid = board.getGrid();

        // Проверяем корректность размещения фишек
        assertEquals(1, grid[5][0]); // нижняя фишка в столбце 0
        assertEquals(2, grid[4][0]); // верхняя фишка в столбце 0
        assertEquals(1, grid[5][3]); // фишка в столбце 3
        assertEquals(2, grid[5][6]); // фишка в столбце 6
        assertEquals(0, grid[3][0]); // пустое место выше фишек
    }

    @Test
    @Tag("getGrid")
    @DisplayName("Тест ссылочной целостности getGrid")
    public void testGetGridReference() {
        Board board = new Board(7, 6);
        board.makeMove(2, 1);

        int[][] grid1 = board.getGrid();
        int[][] grid2 = board.getGrid();

        // Проверяем что возвращается та же ссылка на массив
        assertSame(grid1, grid2);

        // Изменяем доску
        board.makeMove(2, 2);

        // Проверяем что изменения отражаются в уже полученной сетке
        assertEquals(2, grid1[4][2]); // новая фишка должна быть видна
    }

    @Test
    @Tag("getGrid")
    @DisplayName("Тест изменения сетки через getGrid")
    public void testGetGridModification() {
        Board board = new Board(7, 6);
        board.makeMove(1, 1);

        int[][] grid = board.getGrid();

        // Изменяем сетку напрямую
        grid[3][1] = 2;

        // Проверяем что изменение отразилось в доске
        assertEquals(2, board.getCell(1, 3));

        // Проверяем через повторное получение сетки
        int[][] newGrid = board.getGrid();
        assertEquals(2, newGrid[3][1]);
    }

    @Test
    @Tag("getGrid")
    @DisplayName("Тест getGrid для досок разных размеров")
    public void testGetGridDifferentSizes() {
        // Маленькая доска
        Board smallBoard = new Board(3, 4);
        smallBoard.makeMove(1, 1);

        int[][] smallGrid = smallBoard.getGrid();
        assertEquals(4, smallGrid.length); // высота
        assertEquals(3, smallGrid[0].length); // ширина
        assertEquals(1, smallGrid[3][1]); // фишка в нижней позиции

        // Большая доска
        Board bigBoard = new Board(10, 8);
        bigBoard.makeMove(5, 2);

        int[][] bigGrid = bigBoard.getGrid();
        assertEquals(8, bigGrid.length); // высота
        assertEquals(10, bigGrid[0].length); // ширина
        assertEquals(2, bigGrid[7][5]); // фишка в нижней позиции
    }

    @Test
    @Tag("getHeight")
    @DisplayName("Проверка на корректность показателя высоты")
    public void testGetHeight() {
        Board board = new Board(7, 6);
        assertEquals(6, board.getHeight());
    }

    @Test
    @Tag("getWidth")
    @DisplayName("Проверка на корректность показателя широты")
    public void testGetWidth() {
        Board board = new Board(7, 6);
        assertEquals(7, board.getWidth());
    }

    @Test
    @Tag("setMoves")
    @DisplayName("Проверка на корректность установки количества ходов")
    public void testSetMoves() {
        Board board = new Board(7, 6);
        board.setMoves(10);
        assertEquals(10, board.getMoves());
    }

    @Test
    @Tag("getMoves")
    @DisplayName("Проверка на корректность получения количества ходов")
    public void testGetMoves() {
        Board board = new Board(7, 6);
        board.makeMove(1, 1);
        board.makeMove(2, 2);
        assertEquals(2, board.getMoves());
    }

    @Test
    @Tag("switchPlayer")
    @DisplayName("Проверка на корректность смены текущего игрока")
    public void testSwitchPlayer() {
        Board board = new Board(7, 6);
        assertEquals(1, board.getCurrPlayer());
        board.switchPlayer();
        assertEquals(2, board.getCurrPlayer());
        board.switchPlayer();
        assertEquals(1, board.getCurrPlayer());
    }

    // TODO: recreate printBoard test
    @Test
    @Tag("printBoard")
    @DisplayName("Проверка корректности вывода доски в консоль")
    public void testPrintBoard() {
        // Сохраняем оригинальный System.out
        PrintStream originalOut = System.out;

        try {
            // Создаем ByteArrayOutputStream для перехвата вывода
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream testOut = new PrintStream(outputStream);
            System.setOut(testOut);

            // Создаем доску и добавляем фишки
            Board board = new Board(3, 3);
            board.makeMove(0, 1);
            board.makeMove(1, 2);

            // Вызываем метод
            board.printBoard();

            // Получаем вывод
            String output = outputStream.toString();

            // Проверяем что вывод содержит ожидаемые элементы
            assertTrue(output.contains("height: 3 width: 3"));
            assertTrue(output.contains("0 0 0"));
            assertTrue(output.contains("1 2 0"));

        } finally {
            // Восстанавливаем оригинальный System.out
            System.setOut(originalOut);
        }
    }

    @Test
    @Tag("getCell")
    @DisplayName("проверка корретности обрабатывания запроса на определенную клетку")
    public void testGetCell() {
        Board board = new Board(3, 3);
        assertEquals(0, board.getCell(0, 0));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка победы игрока 1 по горизонтали")
    public void testCheckWinHorizontalPlayer1() {
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        board.makeMove(3, 1);

        assertTrue(board.checkWin(1));
        assertFalse(board.checkWin(2));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка победы игрока 2 по вертикали")
    public void testCheckWinVerticalPlayer2() {
        Board board = new Board(7, 6);
        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 2);

        assertTrue(board.checkWin(2));
        assertFalse(board.checkWin(1));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка победы по диагонали слева направо")
    public void testCheckWinDiagonalLeftToRight() {
        Board board = new Board(7, 6);
        // Создаем диагональ для игрока 1
        board.makeMove(0, 1);

        board.makeMove(1, 2);
        board.makeMove(1, 1);

        board.makeMove(2, 2);
        board.makeMove(2, 2);
        board.makeMove(2, 1);

        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 1);

        assertTrue(board.checkWin(1));
        assertFalse(board.checkWin(2));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка победы по диагонали справа налево")
    public void testCheckWinDiagonalRightToLeft() {
        Board board = new Board(7, 6);
        // Создаем диагональ для игрока 2
        board.makeMove(3, 2);

        board.makeMove(2, 1);
        board.makeMove(2, 2);

        board.makeMove(1, 1);
        board.makeMove(1, 1);
        board.makeMove(1, 2);

        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 1);
        board.makeMove(0, 2);

        assertTrue(board.checkWin(2));
        assertFalse(board.checkWin(1));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка отсутствия победы на пустой доске")
    public void testCheckWinEmptyBoard() {
        Board board = new Board(7, 6);

        assertFalse(board.checkWin(1));
        assertFalse(board.checkWin(2));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка отсутствия победы при недостаточном количестве фишек")
    public void testCheckWinInsufficientPieces() {
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 1);
        board.makeMove(2, 1); // только 3 фишки подряд

        assertFalse(board.checkWin(1));
        assertFalse(board.checkWin(2));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка победы в углу доски")
    public void testCheckWinAtCorner() {
        Board board = new Board(7, 6);
        // Победа в правом нижнем углу
        board.makeMove(6, 1);
        board.makeMove(5, 1);
        board.makeMove(4, 1);
        board.makeMove(3, 1);

        assertTrue(board.checkWin(1));
    }

    @Test
    @Tag("checkWin")
    @DisplayName("Проверка победы при смешанных фишках на доске")
    public void testCheckWinMixedPieces() {
        Board board = new Board(7, 6);
        // Добавляем фишки разных игроков
        board.makeMove(0, 1);
        board.makeMove(0, 2);
        board.makeMove(1, 2);
        board.makeMove(1, 1);
        board.makeMove(2, 1);
        board.makeMove(2, 2);
        board.makeMove(3, 2);
        board.makeMove(3, 1);

        // Создаем победную комбинацию для игрока 2 по горизонтали
        board.makeMove(4, 2);
        board.makeMove(5, 2);
        board.makeMove(6, 2);

        assertTrue(board.checkWin(2));
        assertFalse(board.checkWin(1));
    }

    @Test
    @Tag("clear")
    @DisplayName("Проверка очистки доски")
    public void testClearBoard() {
        Board board = new Board(7, 6);
        board.makeMove(0, 1);
        board.makeMove(1, 2);
        board.makeMove(2, 1);
        board.makeMove(3, 2);

        // Проверяем что доска не пуста
        assertNotEquals(0, board.getCell(0, 5));
        assertNotEquals(0, board.getCell(1, 5));
        assertNotEquals(0, board.getCell(2, 5));
        assertNotEquals(0, board.getCell(3, 5));
        assertEquals(4, board.getMoves());

        // Очищаем доску
        board.clear();

        // Проверяем что доска пуста
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertEquals(0, board.getCell(x, y));
            }
        }
        assertEquals(0, board.getMoves());
        assertEquals(1, board.getCurrPlayer()); // текущий игрок должен быть сброшен на 1
    }

    // Добавьте в начало класса BoardTest
    private static class TestBoardChangeListener implements BoardChangeListener {
        public int callCount = 0;
        public int lastX = -1;
        public int lastY = -1;
        public int lastPlayer = -1;

        @Override
        public void onBoardChange(int x, int y, int player) {
            callCount++;
            lastX = x;
            lastY = y;
            lastPlayer = player;
        }

        @Override
        public void addListener(CellClickListener listener) {
            // Пустая реализация для теста
        }

        @Override
        public void printListeners() {
            // Пустая реализация для теста
        }
        @Override
        public void notifyListeners(int x) {
        }
    }

    @Test
    @Tag("addListener")
    @DisplayName("Проверка добавления слушателя")
    public void testAddListener() {
        Board board = new Board(7, 6);
        TestBoardChangeListener listener = new TestBoardChangeListener();

        board.addListener(listener);
        assertEquals(1, board.listeners.size());

        // Проверяем что слушатель вызывается при ходе
        board.makeMove(0, 1);
        assertEquals(1, listener.callCount);
        assertEquals(0, listener.lastX);
        assertEquals(5, listener.lastY); // нижняя позиция в столбце
        assertEquals(1, listener.lastPlayer);
        assertEquals(1, board.listeners.size()); // слушатель остался в списке
    }

    @Test
    @Tag("notifyListeners")
    @DisplayName("Проверка уведомления одного слушателя")
    public void testNotifyListenersOneListener() {
        Board board = new Board(7, 6);
        TestBoardChangeListener listener = new TestBoardChangeListener();

        board.addListener(listener);
        board.notifyListeners(3, 4, 2);

        assertEquals(1, listener.callCount);
        assertEquals(3, listener.lastX);
        assertEquals(4, listener.lastY);
        assertEquals(2, listener.lastPlayer);
    }

    @Test
    @Tag("notifyListeners")
    @DisplayName("Проверка уведомления нескольких слушателей")
    public void testNotifyListenersMultipleListeners() {
        Board board = new Board(7, 6);
        TestBoardChangeListener listener1 = new TestBoardChangeListener();
        TestBoardChangeListener listener2 = new TestBoardChangeListener();
        TestBoardChangeListener listener3 = new TestBoardChangeListener();

        board.addListener(listener1);
        board.addListener(listener2);
        board.addListener(listener3);

        board.notifyListeners(2, 1, 1);

        // Проверяем что все слушатели получили уведомление
        assertEquals(1, listener1.callCount);
        assertEquals(1, listener2.callCount);
        assertEquals(1, listener3.callCount);

        // Проверяем что все получили одинаковые данные
        assertEquals(2, listener1.lastX);
        assertEquals(2, listener2.lastX);
        assertEquals(2, listener3.lastX);
        assertEquals(1, listener1.lastY);
        assertEquals(1, listener2.lastY);
        assertEquals(1, listener3.lastY);
        assertEquals(1, listener1.lastPlayer);
        assertEquals(1, listener2.lastPlayer);
        assertEquals(1, listener3.lastPlayer);
    }

    @Test
    @Tag("notifyListeners")
    @DisplayName("Проверка уведомления без слушателей")
    public void testNotifyListenersNoListeners() {
        Board board = new Board(7, 6);

        // Вызов не должен приводить к исключению
        assertDoesNotThrow(() -> {
            board.notifyListeners(0, 0, 1);
        });
    }

    @Test
    @Tag("notifyListeners")
    @DisplayName("Проверка множественных уведомлений")
    public void testNotifyListenersMultipleCalls() {
        Board board = new Board(7, 6);
        TestBoardChangeListener listener = new TestBoardChangeListener();

        board.addListener(listener);

        board.notifyListeners(1, 2, 1);
        board.notifyListeners(3, 4, 2);
        board.notifyListeners(5, 0, 1);

        assertEquals(3, listener.callCount);
        // Проверяем последний вызов
        assertEquals(5, listener.lastX);
        assertEquals(0, listener.lastY);
        assertEquals(1, listener.lastPlayer);
    }

    @Test
    @Tag("notifyListeners")
    @DisplayName("Проверка уведомления с граничными значениями")
    public void testNotifyListenersBoundaryValues() {
        Board board = new Board(7, 6);
        TestBoardChangeListener listener = new TestBoardChangeListener();

        board.addListener(listener);

        // Тест с минимальными значениями
        board.notifyListeners(0, 0, 1);
        assertEquals(0, listener.lastX);
        assertEquals(0, listener.lastY);
        assertEquals(1, listener.lastPlayer);

        // Тест с максимальными значениями для доски 7x6
        board.notifyListeners(6, 5, 2);
        assertEquals(6, listener.lastX);
        assertEquals(5, listener.lastY);
        assertEquals(2, listener.lastPlayer);
    }
}