import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class CreateCase extends Shell {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			CreateCase shell = new CreateCase(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public CreateCase(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(10, 52, 70, 20);
		lblNewLabel.setText("From:");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setBounds(10, 248, 85, 20);
		lblNewLabel_1.setText("Commision:");
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setBounds(10, 179, 134, 20);
		lblNewLabel_2.setText("Appointment Time:");
		
		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setBounds(10, 116, 70, 20);
		lblNewLabel_3.setText("To:");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(92, 46, 78, 26);
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(92, 116, 78, 26);
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setBounds(182, 173, 78, 26);
		
		text_3 = new Text(this, SWT.BORDER);
		text_3.setBounds(151, 242, 78, 26);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnNewButton.setBounds(10, 338, 272, 30);
		btnNewButton.setText("Enter");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.setBounds(405, 338, 405, 30);
		btnNewButton_1.setText("Random Button");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(1127, 698);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
