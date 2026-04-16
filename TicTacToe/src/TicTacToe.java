import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class TicTacToe {
    //Chiều dài và rộng cảu manf hình
    int boardWidth = 700;
    int boardHeight = 650;

    //Tạo đối tượng màn hình
    JFrame frame = new JFrame("Tic Tac Toe");//Của sổ game và tiêu đề
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();//Tạo đối tượng bàn cờ

    JButton[][] board = new JButton[3][3];//Tạo đối tượng các nút dạng ma trận 3x3
    String playerX = "X";//Dại diện cho ngừoi chơi
    String playerO = "O";
    String currentPlayer = playerX;//Đại diện cho người chơi hiện tại (Mặc định người chơi "X" chơi trước

    boolean gameOver = false;//Biến kết thúc trò chơi mặc định là false
    int turn = 0;//Biến đếm lượt

    //Constructor
    TicTacToe()
    {
        frame.setVisible(true);//Màn hình hiện ra được
        frame.setSize(boardWidth, boardHeight);//Tỉ lệ màn hình
        frame.setLocationRelativeTo(null);//Chỉnh vào giữa màn hình
        frame.setResizable(false);//Chỉnh không thay đổi kích thước
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Nút đóng màn hình
        frame.setLayout(new BorderLayout());//Sắp xếp nút

        textLabel.setBackground(Color.gray);//Màu nền chữ
        textLabel.setForeground(Color.white);//Màu chữ
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));//Font chữ
        textLabel.setHorizontalAlignment(JLabel.CENTER);//Cho chữ vào  màn hình
        textLabel.setText("Tic Tac Toe");//Nội dung chữ
        textLabel.setOpaque(true);//Hiện màu nền (Mặc định là false)

        textPanel.setLayout(new BorderLayout());//Chia bố cục cho chữ
        textPanel.add(textLabel);//Thêm chữ vào khung Panel
        frame.add(textPanel, BorderLayout.NORTH);//Thêm khung Panel vào màn hình game, bố cục ở trên

        boardPanel.setLayout(new GridLayout(3, 3));//Tạo ô 3x3 bằng GridLayout
        boardPanel.setBackground(Color.lightGray);//Màu nền bàn cờ
        frame.add(boardPanel);//Thêm bàn cờ vào cửa sổ game

        for(int r = 0; r < 3; r++)
        {
            for(int c = 0; c < 3; c++)
            {
                JButton tile = new JButton();//Tạo biến tạm
                board[r][c] = tile;//Gắn với ô đang xét
                boardPanel.add(tile);//thêm ô đang xét vào bàn cờ

                tile.setBackground(Color.BLACK);//Màu nền của ô đang xét
                tile.setForeground(Color.white);//Màu nền Font của ô đang xét
                tile.setFont(new Font("Arial", Font.BOLD, 120));//Font chữ cảu ô đang xét
                tile.setFocusable(true);//Hover của nút

                //Tạo biến nhận input
                tile.addActionListener(new ActionListener() {//Hàm chạy khi người dùng click vào
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(gameOver) return;//Nếu trò chơi đã kết thúc sớm hơn số vòng lặp thì trở về
                        JButton tile = (JButton) e.getSource();//Ép đối tượng gây ra sự kiện về kiểu JButton
                        if(tile.getText() == "")//Nếu rỗng thì mới ghi được
                        {
                            tile.setText(currentPlayer);
                            turn++;
                            checkWinner();//Hàm kiểm tra chiến thắng

                            //Đổi lươt
                            if(!gameOver)
                            {
                                if(currentPlayer == playerX)
                                {currentPlayer = playerO;}
                                else{currentPlayer = playerX;}

                                textLabel.setText("Lượt của " + currentPlayer);//In ra lượt của ai
                            }
                        }

                    }
                });
            }
        }
    }

    //Hàm kiểm tra chiến thắng
    void checkWinner()
    {
        //Kiểm tra từ trái qua phải
        for(int r = 0; r < 3; r++)
        {
            //TH1: Nếu phần tử đầu của hàng rỗng thì ko cần kiểm tra tiếp
            if(board[r][0].getText() == "") continue;

            //TH2: 1 dòng có 3 chữ giống nhau thì thắng
            if(board[r][0].getText() == board[r][1].getText() && board[r][1].getText() == board[r][2].getText())
            {
                //nếu tháng thì in màu chữ khác bằng vòng lặp
                for(int i = 0; i < 3; i++)
                {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        //Kiểm tra từ trên xuống
        for(int c = 0; c < 3; c++)
        {
            //TH1: Nếu phần tử đầu của cột rỗng thì ko cần kiểm tra tiếp
            if(board[0][c].getText() == "") continue;

            //TH2: 1 cột có 3 chữ giống nhau thì thắng
            if(board [0][c].getText() == board[1][c].getText() && board [1][c].getText() == board[2][c].getText())
            {
                for(int i = 0; i < 3; i++)
                {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        //Kiểm tra đường chéo
        if(board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "")
        {
            for(int i = 0; i < 3; i++)
            {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        if(board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "")
        {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        //Hòa
        if(turn == 9)
        {
            for(int r = 0; r < 3; r++)
            {
                for(int c = 0; c < 3; c++)
                {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    //Hàm đổi chữ khi chiến thắng
    void setWinner(JButton tile)
    {
        tile.setBackground(Color.darkGray);
        tile.setForeground(Color.green);
        textLabel.setText(currentPlayer + " Chiến thắng");
    }

    void setTie(JButton tile)
    {
        tile.setBackground(Color.darkGray);
        tile.setForeground(Color.yellow);
        textLabel.setText("Hòa");
    }
}