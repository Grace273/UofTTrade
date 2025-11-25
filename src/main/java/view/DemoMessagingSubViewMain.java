package view;

import interface_adapter.messaging.MessagingViewModel;

import javax.swing.*;
import java.awt.*;

public class DemoMessagingSubViewMain {

    public static void main(String[] args) {
        // 为了让 Swing 更好看一点
        SwingUtilities.invokeLater(() -> {
            // 1. 构造 ViewModel，并给它一个假数据
            MessagingViewModel viewModel = new MessagingViewModel();
            MessagingViewModel.State state = viewModel.getState();
            state.title = "Email Alice";
            state.gmailUrl = "https://mail.google.com/mail/?view=cm&fs=1&to=alice@example.com";
            state.error = null;
            viewModel.setState(state);
            viewModel.firePropertyChanged(); // 通知 view

            // 2. 创建 SubView，返回按钮先随便写个打印
            MessagingSubView subView = new MessagingSubView(viewModel, () -> {
                System.out.println("Back button pressed (demo)");
            });

            // 3. 创建一个简单的窗口把它装进去
            JFrame frame = new JFrame("MessagingSubView Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 300);
            frame.setLocationRelativeTo(null);

            frame.setLayout(new BorderLayout());
            frame.add(subView, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
