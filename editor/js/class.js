/**
 * Represents the data for a dynamic class
 *
 * @param {string} name - name of the class
 *
 * @constructor
 */ 
function Class(name) 
{
	this.dataKey = 'attributes';
	this.componentKey = 'classes do not have components';
    this.attribCount = 0;
	
	// Class data
	this.data = [
		new StringValue('Имя класса', 'name', name).setTooltip('Имя класса, не должно содержать цветовых кодов.'),
		new StringValue('Префикс', 'prefix', '&6' + name).setTooltip('Префикс, который получит игрок при выборе класса, поддерживает цветовые кода'),
		new StringValue('Группа', 'group', 'class').setTooltip('Классовая группа, игрок может иметь по одному классу в возможных группах, рекомендовано разделять на группы "class" и "race"'),
		new StringValue('Название маны', 'mana', '&2Мана').setTooltip('Изменяет название "Мана" на любое выбранное, например "Энергия".'),
		new IntValue('Макс.Уровень', 'max-level', 40).setTooltip('Максимальный уровень который может быть получен классом. Если этот класс является родительским для другого класса, то игрок сможет взять следующую класс по достижению этого уровня. Например класс этот класс является родительским для класса Воин, значит по достижению 40 уровня этого класса игрок сможет взять класс Воина.'),
		new ListValue('Родительский класс', 'parent', ['Нет'], 'Нет').setTooltip('По достижению максимального уровня родительского класса, игрок сможет взять этот класс. (Прочтите описание "макс.ур" для подробностей)'),
		new ListValue('Необходимо право', 'needs-permission', ['True', 'False'], 'False').setTooltip('Определяет нужно ли игроку иметь отдельное право для этого класса чтобы выбрать его. Право выглядит так: "skillapi.class.{className}", Где {className] - Имя класса'),
    new ByteListValue('Источники опыта', 'exp-source', [ 'Mob', 'Block Break', 'Block Place', 'Craft', 'Command', 'Special', 'Exp Bottle', 'Smelt', 'Quest' ], 273).setTooltip('Источники с которых данный класс получает опыт. Многие работают только если "use-exp-orbs" включён в config.yml.'),
		new AttributeValue('Нач. Кол-во ХП', 'health', 20, 0).setTooltip('Начальное количество жизней класса'),
		new AttributeValue('Нач Кол-во МП', 'mana', 20, 0).setTooltip('Начальное количество маны класса'),
		new DoubleValue('Регенерация маны', 'mana-regen', 1, 0).setTooltip('Количество маны восстанавливаемое за каждый тик регенерации. Базовый интервал указан в config.yml и по умолчанию имеет 1 секунду. Вы можете указывать десятые и сотые доли в Scale на которые будет увеличиваться регенерация каждый уровень.'),
		new ListValue('Тип Древа скиллов', 'tree', [ 'Basic Horizontal', 'Basic Vertical', 'Level Horizontal', 'Level Vertical', 'Flood', 'Requirement' ], 'Requirement'),
		new StringListValue('Skills (one per line)', 'skills', []).setTooltip('The skills the class is able to use'),
		new ListValue('Icon', 'icon', getMaterials, 'Jack O Lantern').setTooltip('Предмет который будет отображаться в GUI для этого класса'),
		new IntValue('Модель/Прочность', 'icon-data', 0).setTooltip('Определяет модель (CustomModelData 1.14+) или прочность (<1.13) для иконки отображаемой в GUI'),
		new StringListValue('Icon Lore', 'icon-lore', [
			'&d' + name
		]),
		new StringListValue('Ограниченные предметы', 'blacklist', [ ]).setTooltip('Типы предметов которые класс не может использовать (Один тип в одну линию)'),
		new StringValue('Action Bar', 'action-bar', '').setTooltip('Формат Action Bar для этого класса, оставьте пустым для использования стандартного формата (из config.yml)')
	];
    
    this.updateAttribs(10);
}

Class.prototype.updateAttribs = function(i)
{
    var j = 0;
    var back = {};
    while (this.data[i + j] instanceof AttributeValue)
    {
        back[this.data[i + j].key.toLowerCase()] = this.data[i + j];
        j++;
    }
    this.data.splice(i, this.attribCount);
    this.attribCount = 0;
    for (j = 0; j < ATTRIBS.length; j++)
    {
        var attrib = ATTRIBS[j].toLowerCase();
        var format = attrib.charAt(0).toUpperCase() + attrib.substr(1);
        this.data.splice(i + j, 0, new AttributeValue(format, attrib.toLowerCase(), 0, 0)
            .setTooltip('The amount of ' + attrib + ' the class should have')
        );
        if (back[attrib]) 
        {
            var old = back[attrib];
            this.data[i + j].base = old.base;
            this.data[i + j].scale = old.scale;
        }
        this.attribCount++;
    }
};

/**
 * Creates the form HTML for editing the class and applies it to
 * the appropriate area on the page
 */
Class.prototype.createFormHTML = function()
{
	var form = document.createElement('form');
	
	var header = document.createElement('h4');
	header.innerHTML = 'Class Details';
	form.appendChild(header);
	
	var h = document.createElement('hr');
	form.appendChild(h);
	
	this.data[5].list.splice(1, this.data[5].list.length - 1);
	for (var i = 0; i < classes.length; i++)
	{
		if (classes[i] != this) 
		{
			this.data[5].list.push(classes[i].data[0].value);
		}
	}
	for (var i = 0; i < this.data.length; i++)
	{
		this.data[i].createHTML(form);
        
        // Append attributes
        if (this.data[i].name == 'Mana')
        {
            var dragInstructions = document.createElement('label');
            dragInstructions.id = 'attribute-label';
            dragInstructions.innerHTML = 'Drag/Drop your attributes.yml file to see custom attributes';
            form.appendChild(dragInstructions);
            this.updateAttribs(i + 1);
        }
	}
	
	var hr = document.createElement('hr');
	form.appendChild(hr);
	
	var save = document.createElement('h5');
	save.innerHTML = 'Save Class',
	save.classData = this;
	save.addEventListener('click', function(e) {
		this.classData.update();
		saveToFile(this.classData.data[0].value + '.yml', this.classData.getSaveString());
	});
	form.appendChild(save);
	
	var del = document.createElement('h5');
	del.innerHTML = 'Delete',
	del.className = 'cancelButton';
	del.addEventListener('click', function(e) {
		var list = document.getElementById('classList');
		var index = list.selectedIndex;
		
		classes.splice(index, 1);
		if (classes.length == 0)
		{
			newClass();
		}
		list.remove(index);
		index = Math.min(index, classes.length - 1);
		activeClass = classes[index];
		list.selectedIndex = index;
	});
	form.appendChild(del);
	
	var target = document.getElementById('classForm');
	target.innerHTML = '';
	target.appendChild(form);
};

/**
 * Updates the class data from the details form if it exists
 */
Class.prototype.update = function()
{
	var index;
	var list = document.getElementById('classList');
	for (var i = 0; i < classes.length; i++)
	{
		if (classes[i] == this)
		{
			index = i;
			break;
		}
	}
	var prevName = this.data[0].value;
	for (var j = 0; j < this.data.length; j++)
	{
		this.data[j].update();
	}
	var newName = this.data[0].value;
	this.data[0].value = prevName;
	if (isClassNameTaken(newName)) return;
	this.data[0].value = newName;
	list[index].text = this.data[0].value;
};

/**
 * Creates and returns a save string for the class
 */ 
Class.prototype.getSaveString = function()
{
	var saveString = '';
	
	saveString += this.data[0].value + ":\n";
	for (var i = 0; i < this.data.length; i++)
	{
		if (this.data[i] instanceof AttributeValue) continue;
		saveString += this.data[i].getSaveString('  ');
	}
	saveString += '  attributes:\n';
	for (var i = 0; i < this.data.length; i++)
	{
		if (this.data[i] instanceof AttributeValue)
		{
			saveString += this.data[i].getSaveString('    ');
		}
	}
	return saveString;
};

/**
 * Loads class data from the config lines stating at the given index
 *
 * @param {YAMLObject} data - the data to load
 *
 * @returns {Number} the index of the last line of data for this class
 */
Class.prototype.load = loadSection;

/**
 * Creates a new class and switches the view to it
 *
 * @returns {Class} the new class
 */ 
function newClass()
{
	var id = 1;
	while (isClassNameTaken('Class ' + id)) id++;
	
	activeClass = addClass('Class ' + id);
	
	var list = document.getElementById('classList');
	list.selectedIndex = list.length - 2;
	
	activeClass.createFormHTML();
	
	return activeClass;
}

/**
 * Adds a skill to the editor without switching the view to it
 *
 * @param {string} name - the name of the skill to add
 *
 * @returns {Skill} the added skill
 */ 
function addClass(name) 
{
	var c = new Class(name);
	classes.push(c);
	
	var option = document.createElement('option');
	option.text = name;
	var list = document.getElementById('classList');
	list.add(option, list.length - 1);
	
	return c;
}

/**
 * Checks whether or not a class name is currently taken
 *
 * @param {string} name - name to check for
 *
 * @returns {boolean} true if the name is taken, false otherwise
 */ 
function isClassNameTaken(name)
{
	return getClass(name) != null;
}

/**
 * Retrieves a class by name
 *
 * @param {string} name - name of the class to retrieve
 *
 * @returns {Class} the class with the given name or null if not found
 */
function getClass(name)
{
	name = name.toLowerCase();
	for (var i = 0; i < classes.length; i++)
	{
		if (classes[i].data[0].value.toLowerCase() == name) return classes[i];
	}
	return null;
}

var activeClass = new Class('Class 1');
var classes = [activeClass];
activeClass.createFormHTML();
