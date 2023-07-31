package org.sp.shop.admin.view.product;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.sp.shop.admin.domain.SubCategory;
import org.sp.shop.admin.domain.TopCategory;
import org.sp.shop.admin.model.SubCategoryDAO;
import org.sp.shop.admin.model.TopCategoryDAO;

import util.DBManager;

//상품) 등록화면
public class RegistPage extends ProductSubPage {
	JComboBox box_top; // 상위 카테고리
	JComboBox box_sub; // 하위 카테고리

	JTextField t_product_name;
	JTextField t_brand;
	JTextField t_price;

	// 대표이미지는 파일 탐색기로 대체
	JLabel la_path; // 선택한 이미지 경로 출력
	JButton bt_file; // 파일탐색기 띄우는 버튼
	JPanel p_preview; // 선택한 이미지 미리보기
	JTextArea area; // 상세내용 입력
	JScrollPane scroll;

	JPanel p_content; // 너비 700x높이 500
	TopCategoryDAO topCategoryDAO;
	SubCategoryDAO subCategoryDAO;

	List<TopCategory> topList; // 콤보박스에 채워넣을 원본 데이터 DTO가 들어있음
	
	JFileChooser chooser;
	Image image; //파일탐색기에서 선택한 바로 그 파일
	
	JButton bt_regist; //등록버튼
	
	DBManager dbManager;

	public RegistPage() {
		box_top = new JComboBox();
		box_sub = new JComboBox();
		t_product_name = new JTextField();
		t_brand = new JTextField();
		t_price = new JTextField("0");
		la_path = new JLabel("이미지를 선택하세요");
		bt_file = new JButton("파일찾기");
		p_preview = new JPanel() {
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, 130, 130, p_preview);
				
			}
		}; // 추후 그림을 직접 그릴예정
		area = new JTextArea();
		scroll = new JScrollPane(area);
		p_content = new JPanel();
		dbManager=new DBManager();
		topCategoryDAO = new TopCategoryDAO(dbManager);
		subCategoryDAO = new SubCategoryDAO(dbManager);
		chooser=new JFileChooser("D:/morning/html_workspace/images"); //기본경로
		bt_regist=new JButton("등록");

		// 스타일
		p_content.setPreferredSize(new Dimension(700, 550));

		Dimension d = new Dimension(700, 50);

		box_top.setPreferredSize(d);
		box_sub.setPreferredSize(d);
		t_product_name.setPreferredSize(d);
		t_brand.setPreferredSize(d);
		t_price.setPreferredSize(d);
		la_path.setPreferredSize(new Dimension(600, 50));
		p_preview.setPreferredSize(new Dimension(130, 130));
		scroll.setPreferredSize(new Dimension(700, 100));
		
		p_preview.setBackground(Color.GREEN);

		// 조립
		p_content.add(box_top);
		p_content.add(box_sub);
		p_content.add(t_product_name);
		p_content.add(t_brand);
		p_content.add(t_price);
		p_content.add(la_path);
		p_content.add(bt_file);
		p_content.add(p_preview);
		p_content.add(scroll);
		
		add(p_content);
		add(bt_regist);

		// 콤보박스에 상위 카테고리 채우기
		getTopList();

		// 첫번쨰 콤보박스에 이벤트 연결
		box_top.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = box_top.getSelectedIndex();

				// 인덱스를 이용해 List의 DTO를 추출함
				TopCategory dto = topList.get(index);
				int topcategory_idx = dto.getTopcategory_idx();
				System.out.println("pk값은 " + topcategory_idx);

				getSubList(topcategory_idx);
			}
		});

		// 가격 텍스트필드에 이벤트 연결하기 (!!수정)
		t_price.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				// 입력한 데이터를 대상으로 parseInt를 시도하여, 만일 에러가 발생하면 문자를 넣은 것임
				try {
					Integer.parseInt(t_price.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(RegistPage.this, "숫자를 입력하세요");
					t_price.setText("0");
				}

			}
		});
		
		//파일찾기 버튼에 이벤트 연결
		bt_file.addActionListener((e)->{
			openFile();
		});
		
		bt_regist.addActionListener((e)->{
			regist();
		});
	}
	
	public void regist() {
		
	}
	
	//파일 탐색기를 띄우고, 그 안에서 원하는 이미지 파일을 선택하면 해당 이미지를 얻어와 J패널에 그림
	public void openFile() {
		int result=chooser.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION) {
			//p_preview패널에 그림을 그려넣자
			File file= chooser.getSelectedFile();
			//file객체를 이미지로 변환해보자
			try {
				image=ImageIO.read(file);
				//p_preview 패널의 그림 다시 그리기 요청
				//repaint() -> update() -> paint()
				p_preview.repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getTopList() {
		topList = topCategoryDAO.selectAll();

		// topList에 들어있는 요소들을 꺼내(DTO들을 꺼냄)
		// 콤보박스의 요소로 채워넣음
		for (int i = 0; i < topList.size(); i++) {
			TopCategory topCategory = topList.get(i); // DTO꺼내기
			box_top.addItem(topCategory.getTopname());
		}
	}

	// 서브 카테고리 가져오기
	public void getSubList(int topcategory_idx) {
		List<SubCategory> subList = subCategoryDAO.selectAllByFKey(topcategory_idx);
		// 기존에 이미 등록된 아이템들이 존재한다면 초기화해야함
		box_sub.removeAllItems();
		// 하위 카테고리를 두번째 콤보박스에 채워넣기
		for (int i = 0; i < subList.size(); i++) {
			SubCategory dto = subList.get(i); // dto꺼내기
			box_sub.addItem(dto.getSubname());
		}

	}

}
