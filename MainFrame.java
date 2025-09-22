import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.TreeSet;
import java.io.*;

public class ProjectViewer extends JFrame {
	 private static final String FILE_PATH = "project_state.dat"; 
	private DefaultComboBoxModel<Project> projectComboBoxModel;
    private DefaultComboBoxModel<Task> taskComboBoxModel;
    private DefaultComboBoxModel<Processus> processComboBoxModel;

    private JComboBox<Project> projectComboBox;
    private JComboBox<Task> taskComboBox;
    private JComboBox<Processus> processComboBox;
    
    private TreeSet<RessourceMaterielle> mat;
    private TreeSet<RessourceHumaine> emp;
    private TreeSet<RessourceDivers> div;
    private TreeSet<Processus> processus;
    private TreeSet<Task> task;
    private TreeSet<Project> project;
    private JButton calculateButton;
    private JLabel projectLabel, taskLabel, processLabel, resultLabel;
    private double totalCost = 0.0;
    private int totalDuration = 0;
    private JPanel mainPanel;

    public ProjectViewer() {
        super("O . M . E . G . A - C O M P A N Y  ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        projectComboBoxModel = new DefaultComboBoxModel<>();
        taskComboBoxModel = new DefaultComboBoxModel<>();
        processComboBoxModel = new DefaultComboBoxModel<>();
        mat = new TreeSet<>();
        emp = new TreeSet<>();
        div = new TreeSet<>();
        processus = new TreeSet<>();
        task = new TreeSet<>();
        project = new TreeSet<>();
        totalCost=0.0 ;
        totalDuration=0;



projectComboBox = new JComboBox<>(projectComboBoxModel);
taskComboBox = new JComboBox<>(taskComboBoxModel);
processComboBox = new JComboBox<>(processComboBoxModel);


        projectComboBox = new JComboBox<>(projectComboBoxModel);
        taskComboBox = new JComboBox<>(taskComboBoxModel);
        processComboBox = new JComboBox<>(processComboBoxModel);

        projectComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Project selectedProject = (Project) projectComboBox.getSelectedItem();
                if (selectedProject != null) {
                    taskComboBoxModel.removeAllElements();
                     // Add null as the first element
                    for (Task task : selectedProject.getTasks()) {
                    	taskComboBoxModel.addElement(null);
                    	taskComboBoxModel.addElement(task);
                    }
                    taskComboBox.setSelectedItem(null); // Set the selected item to null
                }
            }
        });

        taskComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task selectedTask = (Task) taskComboBox.getSelectedItem();
                if (selectedTask != null) {
                    processComboBoxModel.removeAllElements();
                    // Add null as the first element
                    for (Processus process : selectedTask.getProcesses()) {
                    	processComboBoxModel.addElement(null);                                                                                         
                    	processComboBoxModel.addElement(process);
                    }
                    processComboBox.setSelectedItem(null); // Set the selected item to null
                }
            }
        });
        calculateButton = new JButton("Calculate Cost");
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Project selectedProject = (Project) projectComboBox.getSelectedItem();
                Task selectedTask = (Task) taskComboBox.getSelectedItem();
                Processus selectedProcess = (Processus) processComboBox.getSelectedItem();
                if (selectedProject != null) {
                    if (selectedTask != null) {
                    	if (selectedProcess != null) {
                    		double processCost = selectedProcess.getCost();
                            resultLabel.setText("Total process Cost: $" + processCost);
                    	} else {
                    	
                    	double taskCost = selectedTask.getCost();
                        resultLabel.setText("Total Task Cost: $" + taskCost);}
                    } else {
                        double projectCost = selectedProject.getCost();
                        resultLabel.setText("Total Project Cost: $" + projectCost);
                    }
                    
                    
                    
                }
            }
        });

        resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Labels
        projectLabel = new JLabel("Projects:");
        taskLabel = new JLabel("Tasks:");
        processLabel = new JLabel("Processes:");

        // Panels for layout
        JPanel projectPanel = new JPanel(new BorderLayout());
        projectPanel.setBorder(new TitledBorder("Select Project"));
        projectPanel.add(projectLabel, BorderLayout.NORTH);
        projectPanel.add(projectComboBox, BorderLayout.CENTER);

        JPanel taskPanel = new JPanel(new BorderLayout());
        taskPanel.setBorder(new TitledBorder("Select Task"));
        taskPanel.add(taskLabel, BorderLayout.NORTH);
        taskPanel.add(taskComboBox, BorderLayout.CENTER);

        JPanel processPanel = new JPanel(new BorderLayout());
        processPanel.setBorder(new TitledBorder("Select Process"));
        processPanel.add(processLabel, BorderLayout.NORTH);
        processPanel.add(processComboBox, BorderLayout.CENTER);

        mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        mainPanel.add(projectPanel);
        mainPanel.add(taskPanel);
        mainPanel.add(processPanel);
        mainPanel.add(calculateButton);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveState();
            }
        });

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadState();
            }
        });

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        
        JMenu projectMenu = new JMenu("Project");
        JMenuItem addProjectMenuItem = new JMenuItem("Add Project");
        addProjectMenuItem.addActionListener(e -> openAddProjectFrame());
        projectMenu.add(addProjectMenuItem);

      

        menuBar.add(fileMenu);
        menuBar.add(projectMenu);
       
        setJMenuBar(menuBar);
    }

    private void openAddProjectFrame() {
    	//projectRecalculate();
    	totalCost = 0.0; 
        totalDuration = 0;
    	
        JFrame addProjectFrame = new JFrame("Add New Project");
        addProjectFrame.setSize(600, 400);
        addProjectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField projectNameField = new JTextField();
        JTextField costField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField dureeField = new JTextField();

     

        inputPanel.add(new JLabel("Project Name:"));
        inputPanel.add(projectNameField);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(statusField);

        JLabel costLabel = new JLabel("Cost:");
        costField.setEditable(false);  // Cost is managed by the total cost of processes
        costField.setText(String.valueOf( totalCost));  // Set the total cost
        inputPanel.add(costLabel);
        inputPanel.add(costField);

        JLabel dureeLabel = new JLabel("Duration:");
        dureeField.setEditable(false);  // Duration is managed by the total duration of processes
        dureeField.setText(String.valueOf(totalDuration));  // Set the total duration
        inputPanel.add(dureeLabel);
        inputPanel.add(dureeField);

        JButton calculateButton = new JButton("Calculate Cost and Duration");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculateTotalCostAndDuration();
                costField.setText(String.format("%.2f", totalCost));
                dureeField.setText(String.valueOf(totalDuration));
            }
        });
        

        JButton addProjectButton = new JButton("Add Project");
        addProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
	                String projectName = projectNameField.getText();
	                String status = statusField.getText();

	                // Create a new Task object with the calculated total cost and duration
	                Project newProject= new Project(projectName, totalCost, status, totalDuration);
	                Iterator<Task> it = task.iterator();
	                while (it.hasNext()) {
	                  newProject.addTask(it.next());

	            }
	                // Add the new task to the TreeSet if it doesn't already exist
	                if (!project.contains(newProject)) {
	                    project.add(newProject);  // Add the new task to the TreeSet
	                    projectComboBoxModel.addElement(newProject);
	                    saveState(); 
	                    JOptionPane.showMessageDialog(addProjectFrame, "Project added: " + newProject);
	                    
	                } else {
	                    JOptionPane.showMessageDialog(addProjectFrame, "Project already exists.");
	                }
	                projectNameField.setText("");
	                statusField.setText("");
	                costField.setText("");
	                dureeField.setText("");

	                addProjectFrame.pack();
	                addProjectFrame.dispose();  // Close the frame after adding/
	              
            }});

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(new ActionListener() {
        	 @Override
	            public void actionPerformed(ActionEvent e) { 
        		 
        	openAddTaskFrame();
        	addProjectFrame.dispose();
        }}
        		);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addProjectButton);
        buttonPanel.add(addTaskButton);
        buttonPanel.add(calculateButton);
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        addProjectFrame.add(panel);
        addProjectFrame.setVisible(true);
        
    }
	
	 private void openAddTaskFrame() {
		 
		 totalCost = 0.0; 
		    totalDuration = 0; 
		
		// recalculateTotalCostAndDuration();
		 
		 JFrame addTaskFrame = new JFrame("Add New Task");
	        addTaskFrame.setSize(600, 400);
	        addTaskFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        JPanel panel = new JPanel(new BorderLayout());
	        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
	        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	        JLabel taskNameLabel = new JLabel("Task Name:");
	        JTextField taskNameField = new JTextField();
	        inputPanel.add(taskNameLabel);
	        inputPanel.add(taskNameField);

	        JLabel statusLabel = new JLabel("Status:");
	        JTextField statusField = new JTextField();
	        inputPanel.add(statusLabel);
	        inputPanel.add(statusField);

	        JLabel costLabel = new JLabel("Cost:");
	        JTextField costField = new JTextField();
	        costField.setEditable(false);  // Cost is managed by the total cost of processes
	        costField.setText(String.valueOf( totalCost));  // Set the total cost
	        inputPanel.add(costLabel);
	        inputPanel.add(costField);

	        JLabel dureeLabel = new JLabel("Duration:");
	        JTextField dureeField = new JTextField();
	        dureeField.setEditable(false);  // Duration is managed by the total duration of processes
	        dureeField.setText(String.valueOf(totalDuration));  // Set the total duration
	        inputPanel.add(dureeLabel);
	        inputPanel.add(dureeField);

	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	        JButton addTaskButton = new JButton("Add Task");
	        addTaskButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                String taskName = taskNameField.getText();
	                String status = statusField.getText();

	                // Create a new Task object with the calculated total cost and duration
	                Task newTask = new Task(taskName, totalCost, status, totalDuration);
	                Iterator<Processus> it = processus.iterator();
	                while (it.hasNext()) {
	                  newTask.addProcessus(it.next());
	                
	            }
	                // Add the new task to the TreeSet if it doesn't already exist
	                if (!task.contains(newTask)) {
	                    task.add(newTask);  // Add the new task to the TreeSet
	                    taskComboBoxModel.addElement(newTask);
	                    saveState(); 
	                    JOptionPane.showMessageDialog(addTaskFrame, "Task added: " + newTask);
	                    
	                } else {
	                    JOptionPane.showMessageDialog(addTaskFrame, "Task already exists.");
	                }

	                addTaskFrame.dispose();  // Close the frame after adding
	            openAddProjectFrame();
	            }
	        });
	        buttonPanel.add(addTaskButton);
	        
	        JButton addProcessButton = new JButton("Add Process");
	        addProcessButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                openAddProcessFrame();  // Call the method to open the Add Process Frame
	                addTaskFrame.dispose();             
	            }
	        });
	        buttonPanel.add(addProcessButton);
	        
	        
	      

	       
	        JButton calculateButton = new JButton("Calculate Cost and Duration");
	        calculateButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                recalculateTotalCostAndDuration();
	                costField.setText(String.format("%.2f", totalCost));
	                dureeField.setText(String.valueOf(totalDuration));
	            }
	        });
	        buttonPanel.add(calculateButton);
	        panel.add(inputPanel, BorderLayout.CENTER);
	        panel.add(buttonPanel, BorderLayout.SOUTH);

	        addTaskFrame.add(panel);
	        addTaskFrame.setVisible(true);
	    }

   

    private void openAddProcessFrame() {
    	   JFrame addProcessFrame = new JFrame("Add New Process");
    	  
    	   addProcessFrame.setSize(600, 400);
    	   addProcessFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField processNameField = new JTextField();
        inputPanel.add(new JLabel("Process Name:"));
        inputPanel.add(processNameField);
       
        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField();
        inputPanel.add(statusLabel);
        inputPanel.add(statusField);
        

        JLabel dureeLabel = new JLabel("Duration :");
        JTextField dureeField = new JTextField();
        dureeField.setEditable(false);
        inputPanel.add(dureeLabel);
        inputPanel.add(dureeField);

        
        
        JLabel costLabel = new JLabel("Cost:");
        JTextField costField = new JTextField();
        costField.setEditable(false);  // Cost is calculated or managed elsewhere
        inputPanel.add(costLabel);
        inputPanel.add(costField);

        


        JComboBox<String> resourceTypeComboBox = new JComboBox<>(new String[]{"Materielle", "Humaine", "Divers"});
        JTextField resourceNameField = new JTextField();
        JTextField resourceDetailsField = new JTextField();
        inputPanel.add(new JLabel("Resource Type:"));
        inputPanel.add(resourceTypeComboBox);
        inputPanel.add(new JLabel("Resource Name:"));
        inputPanel.add(resourceNameField);
        
        JPanel resourceFieldsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        resourceFieldsPanel.setBorder(BorderFactory.createTitledBorder("Resource Details"));

        // Fields for Humaine resource type
        JLabel functionLabel = new JLabel("Function:");
        JTextField functionField = new JTextField();
        JLabel specializationLabel = new JLabel("Specialization:");
        JTextField specializationField = new JTextField();
        JLabel workedHoursLabel = new JLabel("Worked Hours:");
        JTextField workedHoursField = new JTextField();
        JLabel hourPriceLabel = new JLabel("Hourly Price:");
        JTextField hourPriceField = new JTextField();

        // Fields for Materielle and Divers resource types
        JLabel typeLabel = new JLabel("Type:");
        JTextField typeField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        JLabel pricePerUnitLabel = new JLabel("Price per Unit:");
        JTextField pricePerUnitField = new JTextField();

        JButton addResourceButton = new JButton("Add Resource");
        addResourceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String selectedType = (String) resourceTypeComboBox.getSelectedItem();
            String resourceName = resourceNameField.getText();

            switch (selectedType) {
                case "Humaine":
                    // Add to the TreeSet and update total cost and duration
                    RessourceHumaine newResourceHumaine = new RessourceHumaine(resourceName, functionField.getText(),
                            specializationField.getText(), Integer.parseInt(workedHoursField.getText()), 
                            Double.parseDouble(hourPriceField.getText()));
                    emp.add(newResourceHumaine);
                    totalCost += newResourceHumaine.getWH() * newResourceHumaine.getHP();
                    totalDuration += newResourceHumaine.getWH();  // Add worked hours to duration
                    break;
                case "Materielle":
                    // Add to the TreeSet and update total cost and duration
                    RessourceMaterielle newResourceMaterielle = new RessourceMaterielle(resourceName, typeField.getText(),
                            Integer.parseInt(quantityField.getText()), Double.parseDouble(pricePerUnitField.getText()));
                    mat.add(newResourceMaterielle);
                    totalCost += newResourceMaterielle.getQuantity() * newResourceMaterielle.getUP();
                    break;
                case "Divers":
                    // Add to the TreeSet and update total cost and duration
                    RessourceDivers newResourceDivers = new RessourceDivers(resourceName, typeField.getText(),
                            Integer.parseInt(quantityField.getText()), Double.parseDouble(pricePerUnitField.getText()));
                    div.add(newResourceDivers);
                    totalCost += newResourceDivers.getQuantity() * newResourceDivers.getUP();
                    break;
            }

            // Update the cost field in the Add Process Frame
            costField.setText(String.format("%.2f", totalCost));
            // Update the duration field in the Add Process Frame
            dureeField.setText(String.valueOf(totalDuration));
            addProcessFrame.pack();

            // Clear resource fields
            resourceNameField.setText("");
            functionField.setText("");
            specializationField.setText("");
            workedHoursField.setText("");
            hourPriceField.setText("");
            typeField.setText("");
            quantityField.setText("");
            pricePerUnitField.setText("");
            }});

        // Initially hide all resource fields
        resourceFieldsPanel.add(functionLabel);
        resourceFieldsPanel.add(functionField);
        resourceFieldsPanel.add(specializationLabel);
        resourceFieldsPanel.add(specializationField);
        resourceFieldsPanel.add(workedHoursLabel);
        resourceFieldsPanel.add(workedHoursField);
        resourceFieldsPanel.add(hourPriceLabel);
        resourceFieldsPanel.add(hourPriceField);
        resourceFieldsPanel.add(typeLabel);
        resourceFieldsPanel.add(typeField);
        resourceFieldsPanel.add(quantityLabel);
        resourceFieldsPanel.add(quantityField);
        resourceFieldsPanel.add(pricePerUnitLabel);
        resourceFieldsPanel.add(pricePerUnitField);
        resourceFieldsPanel.add(new JLabel()); // Placeholder for alignment
        resourceFieldsPanel.add(addResourceButton);
        hideResourceFields();

        // Button to add process
        JButton addProcessButton = new JButton("Add Process");
        String processName = processNameField.getText();
        addProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String processName = processNameField.getText();
                String status = statusField.getText();
                double cost = Double.parseDouble(costField.getText());
                int duration = Integer.parseInt(dureeField.getText());

                Processus process = new Processus(processName, cost, status, duration);

                if (processus.add(process)) {
                	 processComboBoxModel.addElement(process);
                     saveState();
                	JOptionPane.showMessageDialog(addProcessFrame, "Process added: " + process);
                    
                } else {
                    JOptionPane.showMessageDialog(addProcessFrame, "Process already exists.");
                }

                // Clear input fields after adding the process
                processNameField.setText("");
                statusField.setText("");
                costField.setText("");
                dureeField.setText("");

                addProcessFrame.pack();
               
            
                openAddTaskFrame();
                addProcessFrame.dispose();            }
            
        });
        
      
        // Listener to show/hide fields based on resource type selection
        resourceTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedType = (String) e.getItem();
                    switch (selectedType) {
                        case "Humaine":
                            showResourceFields(functionLabel, functionField,
                                    specializationLabel, specializationField,
                                    workedHoursLabel, workedHoursField,
                                    hourPriceLabel, hourPriceField);
                            hideResourceFields(typeLabel, typeField,
                                    quantityLabel, quantityField,
                                    pricePerUnitLabel, pricePerUnitField);
                            break;
                        case "Materielle":
                        case "Divers":
                            showResourceFields(typeLabel, typeField,
                                    quantityLabel, quantityField,
                                    pricePerUnitLabel, pricePerUnitField);
                            hideResourceFields(functionLabel, functionField,
                                    specializationLabel, specializationField,
                                    workedHoursLabel, workedHoursField,
                                    hourPriceLabel, hourPriceField);
                            break;
                    }
                }
            }
        });


        // Add components to panels
        inputPanel.add(new JLabel()); // placeholder for alignment
        inputPanel.add(addProcessButton);
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resourceFieldsPanel, BorderLayout.CENTER);

        addProcessFrame.add(mainPanel);
        addProcessFrame.pack();
        addProcessFrame.setVisible(true);
    }

    // Helper methods to show and hide resource fields based on resource type
    private void showResourceFields(Component... components) {
        for (Component component : components) {
            component.setVisible(true);
        }
    }

    private void hideResourceFields(Component... components) {
        for (Component component : components) {
            component.setVisible(false);
        }
    }

    private void recalculateTotalCostAndDuration() {
        totalCost = 0.0;
        totalDuration = 0;
        Iterator<Processus> iterator = processus.iterator();
        while (iterator.hasNext()) {
            Processus process = iterator.next();
            totalCost += process.getCost();
            totalDuration += process.getDuree();
        }
    }
    
    private void projectRecalculate() {
        totalCost = 0.0;
        totalDuration = 0;
        Iterator<Task> iterator = task.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            totalCost += task.getCost();
            totalDuration += task.getDuree();
            
        }
    
    }
 
    private void saveState() {
    	   
    	try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(mat);
            out.writeObject(emp);
            out.writeObject(div);
            out.writeObject(processus);
            out.writeObject(task);
            out.writeObject(project);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save state.");
        }
    }


    @SuppressWarnings("unchecked")
    private void loadState() {
    	 
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            mat = (TreeSet<RessourceMaterielle>) in.readObject();
            emp = (TreeSet<RessourceHumaine>) in.readObject();
            div = (TreeSet<RessourceDivers>) in.readObject();
            processus = (TreeSet<Processus>) in.readObject();
            task = (TreeSet<Task>) in.readObject();
            project = (TreeSet<Project>) in.readObject();

            // Reinitialize the models
            projectComboBoxModel.removeAllElements();
            taskComboBoxModel.removeAllElements();
            processComboBoxModel.removeAllElements();

            for (Project p : project) {
                projectComboBoxModel.addElement(p);
            }

            for (Task t : task) {
            	taskComboBoxModel.addElement(null);             
            	taskComboBoxModel.addElement(t);
            }

            for (Processus p : processus) {
            	processComboBoxModel.addElement(null);
            	processComboBoxModel.addElement(p);
            }

           
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load state.");
        }
    }

   


    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProjectViewer viewer = new ProjectViewer();
            viewer.setVisible(true);
        });
    }
}
